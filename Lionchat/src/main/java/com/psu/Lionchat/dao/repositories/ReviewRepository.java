package com.psu.Lionchat.dao.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.Review;

/**
 * An ORM mapping of the Reviews table and operations to run on it.
 * 
 * @author jacobkarabin
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByScore(int score);
}
