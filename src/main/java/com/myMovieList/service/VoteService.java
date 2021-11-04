package com.myMovieList.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.model.Movie;
import com.myMovieList.model.User;
import com.myMovieList.model.Vote;
import com.myMovieList.repository.UserRepository;
import com.myMovieList.repository.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

	@Autowired
	private AuthService loggingService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private VoteRepository voteRepo;

	public VoteService() {
		super();
	}

	public VoteService(AuthService loggingService, UserRepository userRepo, VoteRepository voteRepo) {
		super();
		this.loggingService = loggingService;
		this.userRepo = userRepo;
		this.voteRepo = voteRepo;
	}

	@Transactional
	public Vote saveVote(Movie movie, User user, Float voteValue) {

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

		if (movieVote > 10 || movieVote < 0) {
			throw new HandledException("Vote must be between 0 and 10", 400);
		}

		Long userId = loggingService.getLoggedUserId(request);
		User user = userRepo.getById(userId);

		this.saveVote(movie, user, movieVote);

		Long movieId = movie.getId();
		Long voteCount = voteRepo.getVoteCount(movieId);
		Float voteAverage = voteRepo.getVoteSum(movieId) / voteCount;

		movie.setVote_average(((float) Math.round(voteAverage * 10)) / 10);
		movie.setVote_count(voteCount);
	}

}