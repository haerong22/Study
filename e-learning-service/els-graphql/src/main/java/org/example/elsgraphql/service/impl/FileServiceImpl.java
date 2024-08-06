package org.example.elsgraphql.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.elsgraphql.model.CourseSessionFile;
import org.example.elsgraphql.service.FileService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Primary
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String BASE_URL = "http://els-file-manage/sessions/{sessionId}/files";

    private final RestTemplate restTemplate;

    @Override
    public List<CourseSessionFile> findAll() {
        return List.of();
    }

    @Override
    public Optional<CourseSessionFile> findById(Long sessionId, Long fileId) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL + "/{fileId}")
                .buildAndExpand(sessionId, fileId).toUriString();
        CourseSessionFile file = restTemplate.getForObject(url, CourseSessionFile.class);
        return Optional.ofNullable(file);
    }

    @Override
    public List<CourseSessionFile> findFilesBySessionId(Long sessionId) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL)
                .buildAndExpand(sessionId).toUriString();
        CourseSessionFile file = restTemplate.getForObject(url, CourseSessionFile.class);
        return Optional.ofNullable(file).stream().toList();
    }
}
