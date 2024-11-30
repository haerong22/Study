package org.example.trxbatch.config;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class DslConfig {

    @Bean
        public DataSourceConnectionProvider trxBatchConnectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DSLContext trxBatchDsl(DataSource dataSource) {
        return DSL.using(trxBatchConnectionProvider(dataSource), SQLDialect.MYSQL);
    }
}
