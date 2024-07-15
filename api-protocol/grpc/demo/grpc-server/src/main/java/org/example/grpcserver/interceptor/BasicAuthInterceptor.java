package org.example.grpcserver.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Slf4j
@Component
public class BasicAuthInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info("## BasicAuthInterceptor");
        String username = "user";
        String password = "1234";

        String authorization = metadata.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
        if (authorization == null || !authorization.startsWith("Basic ")) {
            serverCall.close(Status.UNAUTHENTICATED.withDescription("Missing or invalid Authorization header"), new Metadata());
            return new ServerCall.Listener<>() {};
        }

        String credentials = authorization.substring(6);

        String[] usernameAndPassword = new String(Base64.getDecoder().decode(credentials)).split(":");
        String requestUsername = usernameAndPassword[0];
        String requestPassword = usernameAndPassword[1];

        if (!requestUsername.equals(username) || !requestPassword.equals(password)) {
            serverCall.close(Status.UNAUTHENTICATED.withDescription("Invalid username or password"), new Metadata());
            return new ServerCall.Listener<>() {};
        }

        return serverCallHandler.startCall(serverCall, metadata);
    }
}
