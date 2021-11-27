package com.psu.Lionchat.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.Question;

/**
 * An ORM mapping of the Questions table and operations to run on it.
 * 
 * @author jacobkarabin
 * */
public interface QuestionRepository extends JpaRepository<Question, Long>{

}
