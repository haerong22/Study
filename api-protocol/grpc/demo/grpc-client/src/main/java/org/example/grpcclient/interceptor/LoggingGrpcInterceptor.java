package org.example.grpcclient.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingGrpcInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        log.info("Received call to {}", methodDescriptor.getFullMethodName());
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {

            @Override
            public void sendMessage(ReqT message) {
                log.debug("Request message: {}", message);
                super.sendMessage(message);

            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                super.start(
                        new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                            @Override
                            public void onMessage(RespT message) {
                                log.info("Response message: {}", message);
                                super.onMessage(message);
                            }

                            @Override
                            public void onHeaders(Metadata headers) {
                                log.info("gRPC headers: {}", headers);
                                super.onHeaders(headers);
                            }

                            @Override
                            public void onClose(Status status, Metadata trailers) {
                                log.info("Interaction ends with status: {}", status);
                                log.info("Trailers: {}", trailers.toString());
                                super.onClose(status, trailers);
                            }
                        }, headers);
            }
        };
    }
}
