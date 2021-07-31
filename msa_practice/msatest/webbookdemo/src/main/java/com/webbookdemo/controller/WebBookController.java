package com.webbookdemo.controller;

import com.webbookdemo.model.dto.WebBookChapterDetailDto;
import com.webbookdemo.model.dto.WebBookChapterDto;
import com.webbookdemo.model.dto.WebBookDto;
import com.webbookdemo.model.form.WebBookChapterRegisterForm;
import com.webbookdemo.model.form.WebBookRegisterForm;
import com.webbookdemo.service.WebBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class WebBookController {

    private final WebBookService webBookService;

    @PostMapping("")
    public ResponseEntity<Long> addWebBook(@RequestBody WebBookRegisterForm webBookRegisterForm) {
        return ResponseEntity.ok().body(webBookService.addWebBook(webBookRegisterForm));
    }

    @PostMapping("/chapter")
    public ResponseEntity<Long> addWebBookChapter(@RequestBody WebBookChapterRegisterForm webBookChapterRegisterForm) {
        return ResponseEntity.ok().body(webBookService.addWebBookChapter(webBookChapterRegisterForm));
    }

    @GetMapping("")
    public ResponseEntity<List<WebBookDto>> getWebBookList() {
        return ResponseEntity.ok().body(webBookService.getWebBookList());
    }

    @GetMapping("/chapter")
    public ResponseEntity<List<WebBookChapterDto>> getWebBookChapterList(Long webBookId) {
        return ResponseEntity.ok().body(webBookService.getWebBookChapterList(webBookId));
    }

    @GetMapping("/chapter/detail")
    public ResponseEntity<WebBookChapterDetailDto> getWebBookChapterDetail(Long webBookChapterId){
        return ResponseEntity.ok(webBookService.getWebBookChapterDetail(webBookChapterId));
    }
}
