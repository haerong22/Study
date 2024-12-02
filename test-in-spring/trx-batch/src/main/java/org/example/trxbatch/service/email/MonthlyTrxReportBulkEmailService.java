package org.example.trxbatch.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trxbatch.exception.TrxBatchEmailServerCommunicationException;
import org.example.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestDto;
import org.example.trxbatch.service.email.dto.BulkReserveResponseData;
import org.example.trxbatch.service.email.dto.EmailServerResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyTrxReportBulkEmailService {

    private final RestClient emailServerRestClient;

    public EmailServerResponse<BulkReserveResponseData> requestBulkReserve(BulkReserveMonthlyTrxReportRequestDto requestDto)
            throws TrxBatchEmailServerCommunicationException {

        return this.emailServerRestClient
                .post()
                .uri("/bulk-reserve")
                .body(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new TrxBatchEmailServerCommunicationException(
                            "Failed to request bulk reserve email");
                })
                .body(new ParameterizedTypeReference<>() {
                });
    }

}