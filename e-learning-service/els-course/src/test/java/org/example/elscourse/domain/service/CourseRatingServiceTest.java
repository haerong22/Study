package org.example.elscourse.domain.service;

import org.example.elscourse.domain.entity.Course;
import org.example.elscourse.domain.entity.CourseRating;
import org.example.elscourse.domain.repository.CourseRatingRepository;
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
class CourseRatingServiceTest {

    @Mock
    private CourseRatingRepository ratingRepository;

    @InjectMocks
    private CourseRatingService courseRatingService;

    @Test
    public void add_rating_to_course_test() {
        // given
        CourseRating rating = new CourseRating();
        Course course = new Course(1L);
        rating.setCourse(course);
        given(ratingRepository.save(any(CourseRating.class))).willReturn(rating);

        // when
        CourseRating result = courseRatingService.addRatingToCourse(1L, rating);

        // then
        assertThat(result).isNotNull();
        verify(ratingRepository).save(rating);
        assertThat(result.getCourse().getId()).isEqualTo(1L);
    }

    @Test
    public void update_rating_test() {
        // given
        CourseRating existingRating = new CourseRating();
        existingRating.setId(1L);
        existingRating.setRating(5);
        existingRating.setComment("Great course!");

        CourseRating updatedDetails = new CourseRating();
        updatedDetails.setRating(4);
        updatedDetails.setComment("Good course.");

        given(ratingRepository.findById(1L)).willReturn(Optional.of(existingRating));
        given(ratingRepository.save(any(CourseRating.class))).willReturn(updatedDetails);

        // when
        CourseRating result = courseRatingService.updateRating(1L, updatedDetails);

        // then
        assertThat(result).isNotNull();
        verify(ratingRepository).findById(1L);
        verify(ratingRepository).save(existingRating);
        assertThat(result.getRating()).isEqualTo(4);
        assertThat(result.getComment()).isEqualTo("Good course.");
    }

    @Test
    public void delete_rating_test() {
        // given

        // when
        courseRatingService.deleteRating(1L);

        // then
        verify(ratingRepository).deleteById(1L);
    }

    @Test
    public void get_all_rating_by_course_id_test() {
        // given
        List<CourseRating> ratings = Arrays.asList(new CourseRating(), new CourseRating());
        given(ratingRepository.findByCourseId(1L)).willReturn(ratings);

        // when
        List<CourseRating> result = courseRatingService.getAllRatingsByCourseId(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        verify(ratingRepository).findByCourseId(1L);
    }

    @Test
    public void get_rating_by_id_test() {
        // given
        CourseRating rating = new CourseRating();
        given(ratingRepository.findById(1L)).willReturn(Optional.of(rating));

        // when
        CourseRating result = courseRatingService.getRatingById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(rating);
        verify(ratingRepository).findById(1L);
    }

}