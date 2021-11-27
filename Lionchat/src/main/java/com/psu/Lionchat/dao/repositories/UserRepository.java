package com.psu.Lionchat.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.User;

/**
 * An ORM mapping of the Users table and operations to run on it.
 * 
 * @author jacobkarabin
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
