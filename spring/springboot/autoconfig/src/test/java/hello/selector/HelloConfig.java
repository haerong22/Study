package hello.selector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {

    @Bean
    public HelloBean helloBean() {
        return new HelloBean();
    }
}
