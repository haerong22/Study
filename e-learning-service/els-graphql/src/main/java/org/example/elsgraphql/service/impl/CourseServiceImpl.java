package org.example.elsgraphql.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.elsgraphql.model.Course;
import org.example.elsgraphql.model.CourseRating;
import org.example.elsgraphql.model.CourseSession;
import org.example.elsgraphql.service.CourseService;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private static final String BASE_URL = "http://els-course/courses";

    @LoadBalanced
    private final RestTemplate restTemplate;

    @Override
    public List<Course> findAllCourses() {
        Course[] courses = restTemplate.getForObject(BASE_URL, Course[].class);
        if (courses == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(courses);
    }

    @Override
    public Optional<Course> findCourseById(Long courseId) {
        Course course = null;
        try {
            course = restTemplate.getForObject(BASE_URL + "/" + courseId, Course.class);
        } catch (Exception e) {
            log.error("An error occurred while fetching the course: {}", e.getMessage(), e);
        }

        return Optional.ofNullable(course);
    }

    @Override
    public Course createCourse(String title, String description, Long instructorId) {
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setInstructorId(instructorId);

        return restTemplate.postForObject(BASE_URL, course, Course.class);
    }

    @Override
    public Course updateCourse(Long id, String title, String description) {
        Course course = new Course();
        course.setId(id);
        course.setTitle(title);
        course.setDescription(description);

        restTemplate.put(BASE_URL + "/" + id, course);
        return course;
    }

    @Override
    public List<CourseSession> findAllSessionsByCourseId(Long courseId) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL + "/{courseId}/sessions")
                .buildAndExpand(courseId).toUriString();
        CourseSession[] sessions = restTemplate.getForObject(url, CourseSession[].class);
        if (sessions == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(sessions)
                .peek(session -> session.setCourseId(courseId))
                .collect(Collectors.toList());
    }

    @Override
    public CourseSession addSessionToCourse(Long courseId, String title) {
        CourseSession courseSession = new CourseSession();
        courseSession.setTitle(title);

        CourseSession addedSession = restTemplate.postForObject(
                UriComponentsBuilder.fromUriString(BASE_URL + "/{courseId}/sessions")
                        .buildAndExpand(courseId).toUri(), courseSession, CourseSession.class);

        if (addedSession != null) {
            addedSession.setCourseId(courseId);
        }

        return addedSession;
    }

    @Override
    public CourseRating addRatingToCourse(Long userId, Long courseId, Integer rating, String comment) {
        CourseRating courseRating = new CourseRating();
        courseRating.setUserId(userId);
        courseRating.setCourseId(courseId);
        courseRating.setRating(rating);
        courseRating.setComment(comment);

        String url = UriComponentsBuilder.fromUriString(BASE_URL + "/{courseId}/ratings")
                .buildAndExpand(courseId).toUriString();
        return restTemplate.postForObject(url, courseRating, CourseRating.class);
    }

    @Override
    public List<Course> findCourseByIds(List<Long> courseIds) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL);
        courseIds.forEach(id -> builder.queryParam("courseIds", id));

        URI uri = builder.build().toUri();

        Course[] courses = restTemplate.getForObject(uri, Course[].class);

        if (courses == null) return Collections.emptyList();

        return Arrays.asList(courses);
    }
}
