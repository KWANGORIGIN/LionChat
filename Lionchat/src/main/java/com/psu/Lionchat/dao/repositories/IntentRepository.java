package com.psu.Lionchat.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.Intent;

/**
 * An ORM mapping of the Intents table and operations to run on it.
 * 
 * @author jacobkarabin
 */
public interface IntentRepository  extends JpaRepository<Intent, Long>{

}
