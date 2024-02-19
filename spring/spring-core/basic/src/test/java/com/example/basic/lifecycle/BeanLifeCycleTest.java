package com.example.basic.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClientInterface bean = ac.getBean(NetworkClientInterface.class);
        NetworkClientMethod bean1 = ac.getBean(NetworkClientMethod.class);
        NetworkClientAnnotation bean2 = ac.getBean(NetworkClientAnnotation.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        @Bean
        public NetworkClientInterface networkClientInterface() {
            NetworkClientInterface networkClient = new NetworkClientInterface();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClientMethod networkClientMethod() {
            NetworkClientMethod networkClient = new NetworkClientMethod();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

        @Bean
        public NetworkClientAnnotation networkClientAnnotation() {
            NetworkClientAnnotation networkClient = new NetworkClientAnnotation();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
