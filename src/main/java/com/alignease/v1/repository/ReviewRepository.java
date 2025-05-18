package com.alignease.v1.repository;

import com.alignease.v1.entity.Review;
import com.alignease.v1.entity.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewStatus(ReviewStatus status);
}
