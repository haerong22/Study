package org.example.elscourse.domain.service;

import org.example.elscourse.domain.entity.CourseSession;
import org.example.elscourse.domain.repository.CourseSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CourseSessionServiceTest {

    @Mock
    private CourseSessionRepository sessionRepository;

    @InjectMocks
    private CourseSessionService courseSessionService;

    @Test
    public void add_session_to_course_test() {
        // given
        Long courseId = 1L;
        CourseSession session = new CourseSession();
        given(sessionRepository.save(any(CourseSession.class))).willReturn(session);

        // when
        CourseSession result = courseSessionService.addSessionToCourse(courseId, session);

        // then
        assertThat(result).isNotNull();
        verify(sessionRepository).save(session);
        assertThat(session.getCourse().getId()).isEqualTo(courseId);
    }

    @Test
    public void update_session_test() {
        // given
        Long sessionId = 1L;
        CourseSession existingSession = new CourseSession();
        existingSession.setId(sessionId);
        existingSession.setTitle("Original Title");

        CourseSession updatedDetails = new CourseSession();
        updatedDetails.setTitle("Updated Title");

        given(sessionRepository.findById(sessionId)).willReturn(Optional.of(existingSession));
        given(sessionRepository.save(any(CourseSession.class))).willReturn(updatedDetails);

        // when
        CourseSession result = courseSessionService.updateSession(sessionId, updatedDetails);

        // then
        assertThat(result).isNotNull();
        verify(sessionRepository).findById(sessionId);
        verify(sessionRepository).save(existingSession);
        assertThat(result.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void get_session_by_id_test() {
        // given
        Long sessionId = 1L;
        CourseSession session = new CourseSession();
        given(sessionRepository.findById(sessionId)).willReturn(Optional.of(session));

        // when
        CourseSession result = courseSessionService.getSessionById(sessionId);

        // then
        assertThat(result).isNotNull().isEqualTo(session);
        verify(sessionRepository).findById(sessionId);
    }

    @Test
    public void get_all_session_by_course_id_test() {
        // given
        Long courseId = 1L;
        List<CourseSession> sessions = Arrays.asList(new CourseSession(), new CourseSession());
        given(sessionRepository.findByCourseId(courseId)).willReturn(sessions);

        // when
        List<CourseSession> result = courseSessionService.getAllSessionsByCourseId(courseId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        verify(sessionRepository).findByCourseId(courseId);
    }

}