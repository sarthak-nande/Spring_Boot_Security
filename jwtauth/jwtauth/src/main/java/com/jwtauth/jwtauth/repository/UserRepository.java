package com.jwtauth.jwtauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwtauth.jwtauth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<String, User>{
	
	Optional<User> findUserByEmail(String email);

	User save(User newUser);

}
