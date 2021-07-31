package com.userdemo.service;

import com.userdemo.client.PaymentClient;
import com.userdemo.client.WebBookClient;
import com.userdemo.model.dto.*;
import com.userdemo.model.entity.ReaderWebBookChapter;
import com.userdemo.model.entity.repository.ReaderWebBookChapterRepository;
import com.userdemo.model.form.WebBookChapterPaymentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderWebBookService {

    private final WebBookClient webBookClient;
    private final PaymentClient paymentClient;
    private final ReaderWebBookChapterRepository readerWebBookChapterRepository;

    public List<WebBookDto> getWebBookList() {
        return webBookClient.getWebBookList();
    }

    public List<ReaderWebBookChapterDto> getWebBookChapterList(Long readerId, Long webBookId) {
        return webBookClient.getWebBookChapterList(webBookId);
    }

    public WebBookChapterDto paymentWebBookChapter(Long readerId, WebBookChapterPaymentForm webBookChapterPaymentForm) {

        // 결제는 paymentClient
        Long webBookChapterPaymentId = paymentClient.webBookChapterPayment(
                WebBookChapterPaymentDto.builder()
                    .webBookChapterId(webBookChapterPaymentForm.getWebBookChapterId())
                    .readerId(readerId)
                    .amount(webBookChapterPaymentForm.getPrice())
                    .build()
        );
        // 가격이 다르면 exception

        // webBookClient 에서 detail 가져온다.
        WebBookChapterDetailDto detail
                = webBookClient.getWebBookChapterDetail(webBookChapterPaymentForm.getWebBookChapterId());

        readerWebBookChapterRepository.save(
                ReaderWebBookChapter.builder()
                        .webBookChapterId(webBookChapterPaymentForm.getWebBookChapterId())
                        .readerId(readerId)
                        .webBookChapterPaymentId(webBookChapterPaymentId)
                        .paymentAmount(webBookChapterPaymentForm.getPrice())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        return WebBookChapterDto.builder()
                .webBookChapterId(webBookChapterPaymentForm.getWebBookChapterId())
                .name(detail.getName())
                .detail(detail.getDetail())
                .createdAt(detail.getCreatedAt())
                .build();
    }
}
