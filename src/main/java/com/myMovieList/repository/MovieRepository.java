package com.myMovieList.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myMovieList.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{

	Optional<Movie> findByName(String title);
	
	@Query(value = "select * from movie m join user_movies um where m.id = um.movies_id and um.user_id = ?1", nativeQuery = true)
	Page<Movie> getMoviesByUserId(Long userId, Pageable pagination);
}
