package com.psu.Lionchat.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.database.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

}
