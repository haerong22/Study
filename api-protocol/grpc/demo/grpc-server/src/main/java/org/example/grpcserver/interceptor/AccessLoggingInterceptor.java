package org.example.grpcserver.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public class AccessLoggingInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {

        Instant startTime = Instant.now();
        String clientIp = metadata.get(Metadata.Key.of("X-Forwarded-For", Metadata.ASCII_STRING_MARSHALLER));

        log.info("{} - - [{}] \"{}\" {}", clientIp, Instant.now(), serverCall.getMethodDescriptor().getFullMethodName(), metadata);

        ServerCall.Listener<ReqT> listener = serverCallHandler.startCall(serverCall, metadata);

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {

            @Override
            public void onComplete() {
                Duration duration = Duration.between(startTime, Instant.now());
                String clientIp = metadata.get(Metadata.Key.of("X-Forwarded-For", Metadata.ASCII_STRING_MARSHALLER));

                log.info("{} - - [{}] \"{}\" {} {}ms", clientIp, Instant.now(), serverCall.getMethodDescriptor().getFullMethodName(), metadata, duration.toMillis());

                super.onComplete();
            }
        };
    }
}
