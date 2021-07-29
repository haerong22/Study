package com.monolithicdemo.controller;

import com.monolithicdemo.model.dto.WebBookChapterDto;
import com.monolithicdemo.model.dto.WebBookDto;
import com.monolithicdemo.model.form.RegisterReaderForm;
import com.monolithicdemo.service.ReaderService;
import com.monolithicdemo.service.WebBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService ReaderService;
    private final WebBookService webBookService;

    @PostMapping("/")
    public ResponseEntity<Long> registerReader(@RequestBody RegisterReaderForm registerReaderForm){
        return ResponseEntity.ok(ReaderService.registerReader(registerReaderForm));
    }

    @GetMapping("/webBook")
    public ResponseEntity<List<WebBookDto>> getWebBookList() {
        return ResponseEntity.ok().body(webBookService.getWebBookList());
    }

    @GetMapping("/{readerId}/webBook/{webBookId}/chapter")
    public ResponseEntity<List<WebBookChapterDto>> getWebBookChapterList(
            @PathVariable(value = "readerId") Long readerId,
            @PathVariable(value = "webBookId") Long webBookId) {
        return ResponseEntity.ok().body(webBookService.getWebBookChapterList(readerId, webBookId));
    }
}
