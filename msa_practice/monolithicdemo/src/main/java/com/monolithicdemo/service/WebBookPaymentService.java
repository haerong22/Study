package com.monolithicdemo.service;

import com.monolithicdemo.model.dto.WebBookChapterPaidDto;
import com.monolithicdemo.model.entity.ReaderWebBookPayment;
import com.monolithicdemo.model.entity.WebBookChapter;
import com.monolithicdemo.model.form.WebBookChapterPaymentForm;
import com.monolithicdemo.repository.ReaderWebBookPaymentRepository;
import com.monolithicdemo.repository.WebBookChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebBookPaymentService {

    private final WebBookChapterRepository webBookChapterRepository;
    private final ReaderWebBookPaymentRepository readerWebBookPaymentRepository;

    public WebBookChapterPaidDto payment(Long readerId, WebBookChapterPaymentForm webBookChapterPaymentForm) {

        ReaderWebBookPayment readerWebBookPayment = readerWebBookPaymentRepository.findByReaderIdAndWebBookChapterId(readerId, webBookChapterPaymentForm.getWebBookChapterId());

        if (readerWebBookPayment != null) {
            return null; // 이미 결제
        }

        WebBookChapter webBookChapter = webBookChapterRepository.findById(webBookChapterPaymentForm.getWebBookChapterId()).get();

        if (!webBookChapter.getPrice().equals(webBookChapterPaymentForm.getPrice())) {
            return null;
        }


        readerWebBookPaymentRepository.save(
                ReaderWebBookPayment.builder()
                        .webBookChapterId(webBookChapterPaymentForm.getWebBookChapterId())
                        .readerId(readerId)
                        .paymentAmount(webBookChapterPaymentForm.getPrice())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        return WebBookChapterPaidDto.from(webBookChapter);
    }
}
