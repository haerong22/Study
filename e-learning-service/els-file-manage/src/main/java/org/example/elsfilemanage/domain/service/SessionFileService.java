package org.example.elsfilemanage.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.elsfilemanage.domain.entity.SessionFile;
import org.example.elsfilemanage.domain.repository.SessionFileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionFileService {

    private final SessionFileRepository sessionFileRepository;

    public Optional<SessionFile> findTopBySessionIdOrderByFileIdDesc(Long sessionId) {
        return sessionFileRepository.findTopBySessionIdOrderByIdDesc(sessionId);
    }

    public Optional<SessionFile> findFileById(Long fileId) {
        return sessionFileRepository.findById(fileId);
    }

    public SessionFile saveFile(SessionFile sessionFile) {
        return sessionFileRepository.save(sessionFile);
    }

    public void deleteFile(Long fileId) {
        sessionFileRepository.deleteById(fileId);
    }
}