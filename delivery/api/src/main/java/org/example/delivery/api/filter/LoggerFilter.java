package org.example.delivery.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class LoggerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var req = new ContentCachingRequestWrapper(request);
        var res = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(req, res);

        // request
        Enumeration<String> headerNames = req.getHeaderNames();
        StringBuilder headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(key -> {
            String value = req.getHeader(key);

            headerValues.append("[").append(key).append(" : ").append(value).append(", ").append("] ");
        });

        String requestBody = new String(req.getContentAsByteArray());
        String uri = req.getRequestURI();
        String method = req.getMethod();

        log.info(">>>>> uri: {}, method: {}, header: {}, body: {}",uri, method, headerValues, requestBody);

        // response
        StringBuilder responseHeaderValues = new StringBuilder();

        res.getHeaderNames().forEach(key -> {
            String value = res.getHeader(key);

            responseHeaderValues.append("[").append(key).append(" : ").append(value).append(", ").append("] ");
        });

        String responseBody = new String(res.getContentAsByteArray());

//        log.info("<<<<< uri: {}, method: {}, header: {}, body: {}",uri, method, responseHeaderValues, responseBody);

        res.copyBodyToResponse();
    }
}
