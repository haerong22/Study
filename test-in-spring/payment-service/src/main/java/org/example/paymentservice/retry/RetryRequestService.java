package org.example.paymentservice.retry;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentservice.checkout.ConfirmRequest;
import org.example.paymentservice.processing.PaymentProcessingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class RetryRequestService {
    private final RetryRequestRepository retryRequestRepository;

    private final PaymentProcessingService paymentProcessingService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void retry(Long retryRequestId) {
        final RetryRequest request = retryRequestRepository
                .findById(retryRequestId)
                .orElseThrow();
        // TODO 정책 확장

        final ConfirmRequest confirmRequest = objectMapper.readValue(
                request.getRequestJson(), ConfirmRequest.class);

        try {
            paymentProcessingService.createCharge(confirmRequest, true);
            request.setStatus(RetryRequest.Status.SUCCESS);
        } catch (Exception e) {
            // 재시도 가능한 오류 retryCount 를 올려준다.
            request.setRetryCount(request.getRetryCount() + 1);
            // TODO 재시도 불가능한 오류 FAILURE 로 저장
        } finally {
            request.setUpdatedAt(LocalDateTime.now());
            retryRequestRepository.save(request);
        }

    }
}