package com.psu.Lionchat.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.InappropriateQuestion;

/**
 * An ORM mapping of the Inappropriate Questions table and operations to run on
 * it.
 * 
 * @author jacobkarabin
 */
public interface InappropriateQuestionRepository extends JpaRepository<InappropriateQuestion, Long> {

}
