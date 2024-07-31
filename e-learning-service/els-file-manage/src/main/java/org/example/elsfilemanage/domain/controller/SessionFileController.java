package org.example.elsfilemanage.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.elsfilemanage.domain.entity.SessionFile;
import org.example.elsfilemanage.domain.service.FileStorageService;
import org.example.elsfilemanage.domain.service.SessionFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sessions/{sessionId}/files")
@RequiredArgsConstructor
public class SessionFileController {

    private final SessionFileService sessionFileService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<SessionFile> getRecentFileBySessionId(
            @PathVariable Long sessionId
    ) {
        return sessionFileService.findTopBySessionIdOrderByFileIdDesc(sessionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<SessionFile> getFileById(
            @PathVariable String sessionId,
            @PathVariable Long fileId
    ) {
        return sessionFileService.findFileById(fileId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SessionFile> uploadFile(
            @PathVariable Long sessionId,
            @RequestParam("file") MultipartFile file
    ) {
        SessionFile storedFile = fileStorageService.storeFile(file, sessionId);
        return ResponseEntity.ok(sessionFileService.saveFile(storedFile));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable String sessionId,
            @PathVariable Long fileId
    ) {
        sessionFileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}