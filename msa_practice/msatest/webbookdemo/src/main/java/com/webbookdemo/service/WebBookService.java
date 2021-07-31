package com.webbookdemo.service;

import com.webbookdemo.model.entity.WebBook;
import com.webbookdemo.model.entity.WebBookChapter;
import com.webbookdemo.model.entity.repository.WebBookChapterRepository;
import com.webbookdemo.model.entity.repository.WebBookRepository;
import com.webbookdemo.model.form.WebBookChapterRegisterForm;
import com.webbookdemo.model.form.WebBookRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebBookService {

    private final WebBookRepository webBookRepository;
    private final WebBookChapterRepository webBookChapterRepository;

    public Long addWebBook(WebBookRegisterForm webBookRegisterForm) {

        return webBookRepository.save(
                WebBook.builder()
                        .writerId(webBookRegisterForm.getWriterId())
                        .name(webBookRegisterForm.getName())
                        .description(webBookRegisterForm.getDescription())
                        .createdAt(LocalDateTime.now())
                        .build()
        ).getWebBookId();
    }

    public Long addWebBookChapter(WebBookChapterRegisterForm webBookChapterRegisterForm) {

        if (webBookRepository.existsById(webBookChapterRegisterForm.getWebBookId())) {
            return webBookChapterRepository.save(
                    WebBookChapter.builder()
                            .webBookId(webBookChapterRegisterForm.getWebBookId())
                            .name(webBookChapterRegisterForm.getName())
                            .detail(webBookChapterRegisterForm.getDetail())
                            .price(webBookChapterRegisterForm.getPrice())
                            .build()
            ).getWebBookChapterId();
        } else {
            return null;
        }
    }
}
