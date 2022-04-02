package com.psu.Lionchat.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.Question;
import com.psu.Lionchat.dao.entities.User;

/**
 * An ORM mapping of the Questions table and operations to run on it.
 * 
 * @author jacobkarabin
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
	Question getById(Long id);
}
