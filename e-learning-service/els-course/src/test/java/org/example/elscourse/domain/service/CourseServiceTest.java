package org.example.elscourse.domain.service;

import org.example.elscourse.domain.entity.Course;
import org.example.elscourse.domain.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void save_course_test() {
        // given
        Course course = new Course();
        given(courseRepository.save(any(Course.class))).willReturn(course);

        // when
        Course result = courseService.saveCourse(course);

        // then
        assertThat(result).isNotNull();
        verify(courseRepository).save(course);
    }

    @Test
    void update_course_test() {
        // given
        Course existingCourse = new Course();
        existingCourse.setId(1L);
        existingCourse.setTitle("Original Title");
        existingCourse.setDescription("Original Description");
        existingCourse.setInstructorId(100L);

        Course updatedDetails = new Course();
        updatedDetails.setTitle("Updated Title");
        updatedDetails.setDescription("Updated Description");
        updatedDetails.setInstructorId(101L);

        given(courseRepository.findById(1L)).willReturn(Optional.of(existingCourse));
        given(courseRepository.save(any(Course.class))).willReturn(updatedDetails);

        // when
        Course result = courseService.updateCourse(1L, updatedDetails);

        // then
        assertThat(result).isNotNull();
        verify(courseRepository).findById(1L);
        verify(courseRepository).save(existingCourse);
        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        assertThat(result.getInstructorId()).isEqualTo(101L);
    }

    @Test
    void get_course_by_id_test() {
        // given
        Course course = new Course();
        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        // when
        Course result = courseService.getCourse(1L);

        // then
        assertThat(result).isNotNull().isEqualTo(course);
    }

    @Test
    void get_all_courses_test() {
        // given
        List<Course> courses = Arrays.asList(new Course(), new Course());
        given(courseRepository.findAll()).willReturn(courses);

        // when
        List<Course> result = courseService.getAllCourses();

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        verify(courseRepository).findAll();
    }

}