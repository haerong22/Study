package org.example.grpcclient.config;

import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.example.grpcclient.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalClientInterceptorConfig {

    @GrpcGlobalClientInterceptor
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

}
