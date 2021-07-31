package com.webbookdemo.controller;

import com.webbookdemo.model.form.WebBookChapterRegisterForm;
import com.webbookdemo.model.form.WebBookRegisterForm;
import com.webbookdemo.service.WebBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class WebBookController {

    private final WebBookService webBookService;

    @PostMapping
    public ResponseEntity<Long> addWebBook(@RequestBody WebBookRegisterForm webBookRegisterForm) {
        return ResponseEntity.ok().body(webBookService.addWebBook(webBookRegisterForm));
    }

    @PostMapping("/chapter")
    public ResponseEntity<Long> addWebBookChapter(@RequestBody WebBookChapterRegisterForm webBookChapterRegisterForm) {
        return ResponseEntity.ok().body(webBookService.addWebBookChapter(webBookChapterRegisterForm));
    }

}
