package hello.config;

import hello.datasource.MyDataSource;
import hello.datasource.MyDatasourcePropertiesV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(MyDatasourcePropertiesV3.class)
public class MyDataSourceConfigV3 {

    private final MyDatasourcePropertiesV3 properties;

    @Bean
    public MyDataSource dataSource() {
        return new MyDataSource(
                properties.getUrl(),
                properties.getUsername(),
                properties.getPassword(),
                properties.getEtc().getMaxConnection(),
                properties.getEtc().getTimeout(),
                properties.getEtc().getOptions()
        );
    }
}
