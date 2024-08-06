package org.example.elsgraphql.service;

import org.example.elsgraphql.model.CourseSessionFile;

import java.util.List;
import java.util.Optional;

public interface FileService {
    List<CourseSessionFile> findAll();

    Optional<CourseSessionFile> findById(Long sessionId, Long fileId);

    List<CourseSessionFile> findFilesBySessionId(Long sessionId);
}
