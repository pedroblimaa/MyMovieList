package com.myMovieList.service;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.model.Movie;

public class VoteServiceTest {

	private VoteService voteService;

	private HttpServletRequest mockedRequest;

	@BeforeEach
	public void setUp() {

		voteService = new VoteService();
		mockedRequest = Mockito.mock(HttpServletRequest.class);
	}

	@Test
	void shouldThrowException_WhenVoteNullIsGiven() {

		Movie movie = createMovie();

		try {
			voteService.addVote(movie, null, mockedRequest);
		} catch (HandledException e) {
			// TODO: handle exception
		}
	}

	private Movie createMovie() {

		Movie movie = new Movie(1, "Movie 1", "This is the first movie", "en", "2020-19-05");

		return movie;
	}
}
