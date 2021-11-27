package com.psu.Lionchat.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psu.Lionchat.dao.entities.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Long>{

}
