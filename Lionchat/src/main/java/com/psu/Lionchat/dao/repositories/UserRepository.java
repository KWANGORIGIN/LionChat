package com.psu.Lionchat.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
