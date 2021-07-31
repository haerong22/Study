package com.userdemo.service;

import com.userdemo.client.WebBookClient;
import com.userdemo.client.form.WebBookRegisterForm;
import com.userdemo.model.entity.repository.WriterRepository;
import com.userdemo.model.form.RegisterWebBookForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WriterWebBookService {

    private final WriterRepository writerRepository;
    private final WebBookClient webBookClient;

    public Long registerWebBook(Long writerId, RegisterWebBookForm registerWebBookForm) {

        if (writerRepository.existsById(writerId)) {
            WebBookRegisterForm webBookRegisterForm = WebBookRegisterForm.builder()
                    .writerId(writerId)
                    .name(registerWebBookForm.getName())
                    .description(registerWebBookForm.getDescription())
                    .build();

            return webBookClient.addBook(webBookRegisterForm);
        } else {
            return null;
        }
    }

//    public Long registerWebBookChapter(Long writerId, Long webBookId, RegisterWebBookChapterForm registerWebBookChapterForm) {
//        if (writerRepository.existsById(writerId) && webBookRepository.existsById(webBookId)) {
//            return webBookChapterRepository.save(
//                    WebBookChapter.builder()
//                            .name(registerWebBookChapterForm.getName())
//                            .detail(registerWebBookChapterForm.getDetail())
//                            .createdAt(LocalDateTime.now())
//                            .price(registerWebBookChapterForm.getPrice())
//                            .build())
//                    .getWebBookChapterId();
//        } else {
//            return null;
//        }
//    }
}
