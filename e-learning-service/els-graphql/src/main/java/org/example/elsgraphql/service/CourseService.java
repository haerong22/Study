package org.example.elsgraphql.service;

import org.example.elsgraphql.model.Course;
import org.example.elsgraphql.model.CourseRating;
import org.example.elsgraphql.model.CourseSession;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAllCourses();

    Optional<Course> findCourseById(Long courseId);

    Course createCourse(String title, String description, Long instructorId);

    Course updateCourse(Long id, String title, String description);

    List<CourseSession> findAllSessionsByCourseId(Long courseId);

    CourseSession addSessionToCourse(Long courseId, String title);

    CourseRating addRatingToCourse(Long userId, Long courseId, Integer rating, String comment);

    List<Course> findCourseByIds(List<Long> ids);
}
