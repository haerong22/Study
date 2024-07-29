package org.example.elscourse.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.elscourse.domain.entity.Course;
import org.example.elscourse.domain.entity.CourseRating;
import org.example.elscourse.domain.repository.CourseRatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseRatingService {

    private final CourseRatingRepository ratingRepository;

    public CourseRating addRatingToCourse(Long courseId, CourseRating rating) {
        rating.setCourse(new Course(courseId));
        return ratingRepository.save(rating);
    }

    public CourseRating updateRating(Long ratingId, CourseRating ratingDetails) {
        CourseRating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        rating.setRating(ratingDetails.getRating());
        rating.setComment(ratingDetails.getComment());
        return ratingRepository.save(rating);
    }

    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    public List<CourseRating> getAllRatingsByCourseId(Long courseId) {
        return ratingRepository.findByCourseId(courseId);
    }

    public CourseRating getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
    }
}