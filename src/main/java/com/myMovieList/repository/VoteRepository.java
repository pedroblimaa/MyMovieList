package com.myMovieList.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myMovieList.model.Movie;
import com.myMovieList.model.User;
import com.myMovieList.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long>{
	
	@Query(value = "select sum(vote_value) from vote v join movie m where v.movie_id = m.id and v.movie_id = ?", nativeQuery = true)
	Float getVoteSum(Long movieId);
	
	@Query(value = "select count(*) from vote v join movie m where v.movie_id = m.id and v.movie_id = ?", nativeQuery = true)
	Long getVoteCount(Long movieId);

	Optional<Vote> findByMovieAndUser(Movie movie, User user);
}
