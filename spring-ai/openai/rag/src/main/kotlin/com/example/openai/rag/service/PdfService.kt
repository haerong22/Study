package com.example.openai.rag.service

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service


@Service
class PdfService(
    private val chatModel: ChatModel,
    private var vectorStore: VectorStore,
) {

    // # 6.단계 : 프롬프트 생성(Create Prompt)
    private val prompt = """
            You are an assistant for question-answering tasks.
            Use the following pieces of retrieved context to answer the question.
            If you don't know the answer, just say that you don.t know.
            Answer in Korean.  
            
            #Question:
            {input}               
            
            #Context :
            {documents}
                                         
            #Answer:                                    
            
            """.trimIndent()


    fun simplify(question: String): String {
        val template = PromptTemplate(prompt)
        val promptsParameters: MutableMap<String, Any> = HashMap()
        promptsParameters["input"] = question
        promptsParameters["documents"] = findSimilarData(question)
        return chatModel
            .call(template.create(promptsParameters))
            .result
            .output
            .content
    }

    // # 5.단계 - 검색기(Retriever) 생성---|(Question)<---유사도 검색(similarity)
    // 문서에 포홤되어 있는 정보를 검색하고 생성
    private fun findSimilarData(question: String): String {
        return vectorStore.similaritySearch(
            SearchRequest.query(question).withTopK(2)
        ).joinToString(separator = "") { it.content.toString() }
    }
}