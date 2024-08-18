package org.example.elscourse.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.elscourse.domain.controller.swagger.CourseControllerSwagger;
import org.example.elscourse.domain.entity.Course;
import org.example.elscourse.domain.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController implements CourseControllerSwagger {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> saveCourse(
            @RequestBody Course course
    ) {
        Course newCourse = courseService.saveCourse(course);
        return ResponseEntity.ok(newCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course
    ) {
        Course updatedCourse = courseService.updateCourse(courseId, course);
        return ResponseEntity.ok(updatedCourse);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(
            @RequestParam(required = false) List<Long> courseIds
    ) {
        List<Course> courses;
        if (courseIds == null || courseIds.isEmpty()) {
            courses = courseService.getAllCourses();
        } else {
            courses = courseService.getCourseByIds(courseIds);
        }

        return ResponseEntity.ok(courses);
    }
}
