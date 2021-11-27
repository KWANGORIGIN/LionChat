package com.psu.Lionchat.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.psu.Lionchat.dao.repositories.ReviewRepository;

@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	public CommandLineRunner generateReviews(ReviewRepository repository) {
		return (args) -> {
//			// save a few reviews
//			repository.save(new Review(1));
//			repository.save(new Review(2));
//			repository.save(new Review(3));
//			repository.save(new Review(5));
//			repository.save(new Review(5));
//			repository.save(new Review(5));
//
//			// fetch all customers
//			log.info("Reviews found with findAll():");
//			log.info("-------------------------------");
//			for (Review review : repository.findAll()) {
//				log.info(review.toString());
//			}
//			log.info("");
//
//			// fetch an individual customer by ID
//			Optional<Review> review = repository.findById(1L);
//			log.info("Review found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(review.toString());
//			log.info("");
//
//			// fetch customers by last name
//			log.info("Review found with findByScore(1):");
//			log.info("--------------------------------------------");
//			repository.findByScore(1).forEach(oneStar -> {
//				log.info(oneStar.toString());
//			});
//			log.info("");
		};
	}
}
