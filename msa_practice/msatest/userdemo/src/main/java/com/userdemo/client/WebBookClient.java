package com.userdemo.client;

import com.userdemo.client.form.WebBookChapterRegisterForm;
import com.userdemo.client.form.WebBookRegisterForm;
import com.userdemo.model.dto.WebBookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "webBook", url = "${external-api.webBook.url}")
public interface WebBookClient {

    @PostMapping("")
    Long addBook(@RequestBody WebBookRegisterForm registerWebBookForm);

    @PostMapping("/chapter")
    Long addWebBookChapter(WebBookChapterRegisterForm webBookChapterRegisterForm);

    @GetMapping("")
    List<WebBookDto> getWebBookList();
}
