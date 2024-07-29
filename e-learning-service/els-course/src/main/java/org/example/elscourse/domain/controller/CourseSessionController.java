package org.example.elscourse.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.elscourse.domain.entity.CourseSession;
import org.example.elscourse.domain.service.CourseSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/sessions")
@RequiredArgsConstructor
public class CourseSessionController {

    private final CourseSessionService sessionService;

    @PostMapping
    public ResponseEntity<CourseSession> addSession(
            @PathVariable Long courseId,
            @RequestBody CourseSession courseSession
    ) {
        CourseSession newSession = sessionService.addSessionToCourse(courseId, courseSession);
        return ResponseEntity.ok(newSession);
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<CourseSession> updateSession(
            @PathVariable Long courseId,
            @PathVariable Long sessionId,
            @RequestBody CourseSession courseSession
    ) {
        CourseSession updatedSession = sessionService.updateSession(sessionId, courseSession);
        return ResponseEntity.ok(updatedSession);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<CourseSession> getSession(
            @PathVariable Long courseId,
            @PathVariable Long sessionId
    ) {
        CourseSession session = sessionService.getSessionById(sessionId);
        return ResponseEntity.ok(session);
    }

    @GetMapping
    public ResponseEntity<List<CourseSession>> getSessions(
            @PathVariable Long courseId
    ) {
        List<CourseSession> sessions = sessionService.getAllSessionsByCourseId(courseId);
        return ResponseEntity.ok(sessions);
    }
}
