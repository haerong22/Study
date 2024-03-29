package com.example.springbatch.application.job;

import com.example.springbatch.application.job.param.CreateArticleJobParam;
import com.example.springbatch.application.model.ArticleModel;
import com.example.springbatch.domain.entity.Article;
import com.example.springbatch.domain.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.time.LocalDateTime;

@Configuration
@Slf4j
public class CreateArticleJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ArticleRepository articleRepository;
    private final CreateArticleJobParam createArticleJobParam;
    private final JdbcTemplate demoJdbcTemplate;
    private final EntityManagerFactory demoEntityManagerFactory;
    private final DataSource demoDataSource;

    public CreateArticleJobConfig(JobBuilderFactory jobBuilderFactory,
                                  StepBuilderFactory stepBuilderFactory,
                                  ArticleRepository articleRepository,
                                  CreateArticleJobParam createArticleJobParam,
                                  @Qualifier("demoJdbcTemplate") JdbcTemplate demoJdbcTemplate,
                                  @Qualifier("demoEntityManagerFactory") EntityManagerFactory demoEntityManagerFactory,
                                  DataSource demoDataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.articleRepository = articleRepository;
        this.createArticleJobParam = createArticleJobParam;
        this.demoJdbcTemplate = demoJdbcTemplate;
        this.demoEntityManagerFactory = demoEntityManagerFactory;
        this.demoDataSource = demoDataSource;
    }

    @Bean
    public Job createArticleJob() {
        return jobBuilderFactory.get("createArticleJob")
                .incrementer(new RunIdIncrementer())
                .start(createArticleStep())
                .build();
    }

    @Bean
    @JobScope
    public Step createArticleStep() {
        return stepBuilderFactory.get("createArticleStep")
                .<ArticleModel, Article>chunk(1000)
                .reader(createArticleReader())
                .processor(createArticleProcessor())
                .writer(createArticleWriterJPA())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ArticleModel> createArticleReader() {
        log.info("PARAM!!!!!! {}", createArticleJobParam.getName());
        return new FlatFileItemReaderBuilder<ArticleModel>()
                .name("createArticleReader")
                .resource(new ClassPathResource("Articles.csv"))
                .delimited()
                .names("title", "content")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ArticleModel.class)
                .build();
    }

    @Bean
    public ItemProcessor<ArticleModel, Article> createArticleProcessor() {
        LocalDateTime now = LocalDateTime.now();
        return articleModel -> Article.builder()
                .title(articleModel.getTitle())
                .content(articleModel.getContent())
                .createdAt(now)
                .build();
    }

    // JDBC
    @Bean
    public ItemWriter<Article> createArticleWriterJDBC() {
        return articles -> demoJdbcTemplate.batchUpdate("insert into Article (title, content, createdAt) values (?, ?, ?)",
                articles,
                1000,
                (ps, article) -> {
                    ps.setObject(1, article.getTitle());
                    ps.setObject(2, article.getContent());
                    ps.setObject(3, article.getCreatedAt());
                });
    }

    // JPA
    @Bean
    public RepositoryItemWriter<Article> createArticleWriterJPA() {
        return new RepositoryItemWriterBuilder<Article>()
                .repository(articleRepository)
                .build();
    }

    // ======================

//    // JDBC
//    @Bean
//    public ItemWriter<Article> writerJDBC() {
//        return new JdbcBatchItemWriterBuilder<Article>()
//                .dataSource(demoDataSource)
//                .build();
//    }

    // JPA
    @Bean
    public ItemWriter<Article> writerJPA() {
        return new JpaItemWriterBuilder<Article>()
                .entityManagerFactory(this.demoEntityManagerFactory)
                .build();
    }
}
