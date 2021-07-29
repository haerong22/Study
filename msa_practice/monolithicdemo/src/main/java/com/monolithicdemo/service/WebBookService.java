package com.monolithicdemo.service;

import com.monolithicdemo.model.dto.WebBookChapterDto;
import com.monolithicdemo.model.dto.WebBookDto;
import com.monolithicdemo.model.entity.ReaderWebBookPayment;
import com.monolithicdemo.repository.ReaderWebBookPaymentRepository;
import com.monolithicdemo.repository.WebBookChapterRepository;
import com.monolithicdemo.repository.WebBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebBookService {

    private final WebBookRepository webBookRepository;
    private final WebBookChapterRepository webBookChapterRepository;
    private final ReaderWebBookPaymentRepository readerWebBookPaymentRepository;

    public List<WebBookDto> getWebBookList() {
        return webBookRepository.findAll().stream()
                .map(webBook -> WebBookDto.from(webBook))
                .collect(Collectors.toList());
    }

    public List<WebBookChapterDto> getWebBookChapterList(Long readerId, Long webBookId) {

        List<ReaderWebBookPayment> readerWebBookPayments = readerWebBookPaymentRepository.findAllByReaderId(readerId);

        List<WebBookChapterDto> webBookChapterDtos = webBookChapterRepository.findAllById(List.of(webBookId)).stream()
                .map(WebBookChapterDto::from)
                .collect(Collectors.toList());

        readerWebBookPayments.forEach(readerWebBookPayment -> {
            WebBookChapterDto webBookChapterDto = webBookChapterDtos.stream().filter(dto -> dto.getWebBookChapterId()
                    .equals(readerWebBookPayment.getWebBookChapterId())).findFirst().get();
            if (webBookChapterDto != null) {
                webBookChapterDto.setIsPaid(true);
            } else {
                webBookChapterDto.setIsPaid(false);
            }
        });
        return webBookChapterDtos;
    }
}
