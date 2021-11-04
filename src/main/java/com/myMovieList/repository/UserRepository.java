package com.myMovieList.repository;

import java.util.Optional;

import com.myMovieList.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}