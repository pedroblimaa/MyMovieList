package com.myMovieList.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myMovieList.config.dto.ErrorHandleDto;
import com.myMovieList.config.exception.HandledException;
import com.myMovieList.model.Movie;
import com.myMovieList.model.User;
import com.myMovieList.model.Vote;
import com.myMovieList.repository.UserRepository;
import com.myMovieList.repository.VoteRepository;

@Service
public class VoteService {

	@Autowired
	private AuthService loggingService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private VoteRepository voteRepo;

	@Transactional
	private Vote saveVote(Movie movie, User user, Float voteValue) {

		Optional<Vote> voteOptional = voteRepo.findByMovieAndUser(movie, user);

		Vote vote;

		if (voteOptional.isPresent()) {
			vote = voteOptional.get();
			vote.setVoteValue(voteValue);

			return vote;
		}

		vote = new Vote(movie, user, voteValue);
		voteRepo.save(vote);
		return vote;
	}

	@Transactional
	public void addVote(Movie movie, Float movieVote, HttpServletRequest request) throws HandledException {

		if (movieVote == null) {
			return;
		}
		
		if(movieVote > 10) {
			throw new HandledException("Max note is 10", 400);
		}

		Long userId = loggingService.getLoggedUserId(request);
		User user = userRepo.getById(userId);
		
		this.saveVote(movie, user, movieVote);
		
		Long movieId = movie.getId();
		Long voteCount = voteRepo.getVoteCount(movieId);

		movie.setVote_average(voteRepo.getVoteSum(movieId)/voteCount);
		movie.setVote_count(voteCount);
	}

}