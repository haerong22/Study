package com.example.openai.rag.config

import jakarta.annotation.PostConstruct
import org.springframework.ai.document.Document
import org.springframework.ai.reader.ExtractedTextFormatter
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.jdbc.core.simple.JdbcClient


@Configuration
class DataLoader(
    vectorStore: VectorStore,
    jdbcClient: JdbcClient,
    @Value("classpath:/SPRi AI Brief_2월호_산업동향_F.pdf")
    private val pdfResource: Resource,
) {
    init {
        val count = jdbcClient.sql("select count(*) from vector_store")
            .query(Int::class.java)
            .single()
        println("No of Records in the PG Vector Store=$count")
        if (count == 0) {
            println("Loading.....")
            // PDF Reader
            val config = PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(
                    ExtractedTextFormatter.builder()
                        .withNumberOfTopTextLinesToDelete(0)
                        .build()
                )
                .withPagesPerDocument(1)
                .build()
            // # 1.단계 : 문서로드(Load Documents)
            val pdfReader = PagePdfDocumentReader(pdfResource, config)
            val documents: List<Document> = pdfReader.get()

            // # 2.단계 : 문서분할(Split Documents)
            val splitter = TokenTextSplitter(1000, 400, 10, 5000, true)
            val splitDocuments: List<Document> = splitter.apply(documents)
            println(splitDocuments.size) // 45
            println(splitDocuments[0]) // 25

            // # 3.단계 : 임베딩(Embedding) -> 4.단계 : DB에 저장(백터스토어 생성)
            vectorStore.accept(splitDocuments) // OpenAI 임베딩을 거친다.
            println("Application is ready to Serve the Requests")
        }
    }
}