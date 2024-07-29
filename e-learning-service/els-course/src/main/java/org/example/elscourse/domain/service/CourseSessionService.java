package org.example.elscourse.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.elscourse.domain.entity.Course;
import org.example.elscourse.domain.entity.CourseSession;
import org.example.elscourse.domain.repository.CourseSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseSessionService {

    private final CourseSessionRepository sessionRepository;


    public CourseSession addSessionToCourse(Long courseId, CourseSession session) {
        session.setCourse(new Course(courseId));
        return sessionRepository.save(session);
    }

    public CourseSession updateSession(Long sessionId, CourseSession sessionDetails) {
        CourseSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id " + sessionId));
        session.setTitle(sessionDetails.getTitle());
        return sessionRepository.save(session);
    }

    public CourseSession getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with id " + sessionId));
    }

    public List<CourseSession> getAllSessionsByCourseId(Long courseId) {
        return sessionRepository.findByCourseId(courseId);
    }
}
