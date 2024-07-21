package com.example.demo.domain.reviews.controller;

import com.example.demo.domain.reviews.entity.Review;
import com.example.demo.domain.reviews.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @QueryMapping
    public List<Review> getReviewsByBookId(@Argument Long id) {
        return reviewService.findByBookId(id);
    }

    @MutationMapping
    public Review addReview(
            @Argument Long bookId,
            @Argument String content,
            @Argument Float rating
    ) {
        return reviewService.saveReview(bookId, content, rating);
    }

    @MutationMapping
    public Map<String, Boolean> deleteReviewById(@Argument Long reviewId) {
        reviewService.deleteById(reviewId);

        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("success", true);

        return resultMap;
    }
}