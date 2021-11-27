package com.psu.Lionchat.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

}
