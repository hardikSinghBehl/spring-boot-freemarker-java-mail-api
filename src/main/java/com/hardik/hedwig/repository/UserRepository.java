package com.hardik.hedwig.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.hedwig.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmailId(String emailId);

}
