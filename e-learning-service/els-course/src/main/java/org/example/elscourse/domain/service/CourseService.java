package org.example.elscourse.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.elscourse.domain.entity.Course;
import org.example.elscourse.domain.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long courseId, Course courseDetail) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        course.setTitle(courseDetail.getTitle());
        course.setDescription(courseDetail.getDescription());
        course.setInstructorId(courseDetail.getInstructorId());
        return courseRepository.save(course);
    }

    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCourseByIds(List<Long> courseIds) {
        return courseRepository.findAllById(courseIds);
    }
}
