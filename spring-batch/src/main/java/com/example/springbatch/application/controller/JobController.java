package com.example.springbatch.application.controller;

import com.example.springbatch.application.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/create-articles")
    public void runCreateArticleJob() throws Exception {
        jobService.runCreateArticleJob();
    }
}
