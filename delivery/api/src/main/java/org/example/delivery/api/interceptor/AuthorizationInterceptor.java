package org.example.delivery.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.delivery.api.domain.token.business.TokenBusiness;
import org.example.delivery.common.error.CommonErrorCode;
import org.example.delivery.common.exception.ApiException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        String userId = request.getHeader("x-user-id");
        if (userId == null) {
            throw new ApiException(CommonErrorCode.BAD_REQUEST, "x-user-id header 없음");
        }

        RequestAttributes requestAttributes = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        requestAttributes.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);

        return true;
    }
}
