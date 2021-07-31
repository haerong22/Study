package com.userdemo.service;

import com.userdemo.client.WebBookClient;
import com.userdemo.model.dto.WebBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderWebBookService {

    private final WebBookClient webBookClient;

    public List<WebBookDto> getWebBookList() {
        return webBookClient.getWebBookList();
    }
}
