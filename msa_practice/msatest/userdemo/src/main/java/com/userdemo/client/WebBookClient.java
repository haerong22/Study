package com.userdemo.client;

import com.userdemo.client.form.WebBookChapterRegisterForm;
import com.userdemo.client.form.WebBookRegisterForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "webBook", url = "${external-api.webBook.url}")
public interface WebBookClient {

    @PostMapping("")
    Long addBook(@RequestBody WebBookRegisterForm registerWebBookForm);

    @PostMapping("/chapter")
    Long addWebBookChapter(WebBookChapterRegisterForm webBookChapterRegisterForm);
}
