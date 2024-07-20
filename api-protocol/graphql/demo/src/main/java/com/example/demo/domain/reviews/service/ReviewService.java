package com.example.demo.domain.reviews.service;

import com.example.demo.domain.books.repository.BookRepository;
import com.example.demo.domain.reviews.entity.Review;
import com.example.demo.domain.reviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public Review saveReview(Long bookId, String content, Float rating) {
        Review newReview = new Review();
        newReview.setBook(bookRepository.findById(bookId).orElseThrow());
        newReview.setContent(content);
        newReview.setRating(rating);
        newReview.setCreatedDate(OffsetDateTime.now());
        return reviewRepository.save(newReview);
    }

    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> findByBookId(Long bookId) {
        return reviewRepository.findByBookIdOrderByCreatedDateDesc(bookId);
    }


    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}