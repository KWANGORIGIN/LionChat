package com.psu.Lionchat.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByScore(int score);
}
