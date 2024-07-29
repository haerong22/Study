package org.example.elscourse.domain.repository;

import org.example.elscourse.domain.entity.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRatingRepository extends JpaRepository<CourseRating, Long> {

    List<CourseRating> findByCourseId(Long courseId);
}