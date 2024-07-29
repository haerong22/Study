package org.example.elscourse.domain.controller;

import lombok.RequiredArgsConstructor;
import org.example.elscourse.domain.entity.CourseRating;
import org.example.elscourse.domain.service.CourseRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/ratings")
@RequiredArgsConstructor
public class CourseRatingController {

    private final CourseRatingService ratingService;

    @PostMapping
    public ResponseEntity<CourseRating> addRating(
            @PathVariable Long courseId,
            @RequestBody CourseRating rating
    ) {
        CourseRating newRating = ratingService.addRatingToCourse(courseId, rating);
        return ResponseEntity.ok(newRating);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<CourseRating> updateRating(
            @PathVariable Long courseId,
            @PathVariable Long ratingId,
            @RequestBody CourseRating rating
    ) {
        CourseRating updatedRating = ratingService.updateRating(ratingId, rating);
        return ResponseEntity.ok(updatedRating);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<CourseRating> deleteRating(
            @PathVariable Long courseId,
            @PathVariable Long ratingId
    ) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseRating>> getRatings(
            @PathVariable Long courseId
    ) {
        List<CourseRating> ratings = ratingService.getAllRatingsByCourseId(courseId);
        return ResponseEntity.ok(ratings);
    }
}
