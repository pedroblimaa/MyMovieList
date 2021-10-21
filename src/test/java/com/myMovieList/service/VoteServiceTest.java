package com.myMovieList.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.model.Movie;
import com.myMovieList.model.User;
import com.myMovieList.model.Vote;
import com.myMovieList.repository.UserRepository;
import com.myMovieList.repository.VoteRepository;

public class VoteServiceTest {

	private VoteService voteService;

	private HttpServletRequest mockedRequest;

	private AuthService loggingService;

	private UserRepository userRepo;

	private VoteRepository voteRepo;

	@BeforeEach
	public void setUp() {

		mockedRequest = Mockito.mock(HttpServletRequest.class);

		loggingService = Mockito.mock(AuthService.class);
		userRepo = Mockito.mock(UserRepository.class);
		voteRepo = Mockito.mock(VoteRepository.class);

		voteService = new VoteService(loggingService, userRepo, voteRepo);
	}

	@Test
	void shouldNotCallAnyMethods_WhenVoteNullIsGiven() throws HandledException {

		Movie movie = createMovie();

		voteService.addVote(movie, null, mockedRequest);

		verify(loggingService, never()).getLoggedUserId(mockedRequest);
		verify(userRepo, never()).getById(Mockito.anyLong());
		verify(voteRepo, never()).getVoteCount(Mockito.anyLong());

	}

	@Test
	void shouldThrowAnException_WhenInvalidVoteIsGiven() throws HandledException {

		Movie movie = createMovie();

		try {
			voteService.addVote(movie, (float) 18, mockedRequest);
			assertTrue(false);
		} catch (HandledException e) {
			assertEquals("Vote must be between 0 and 10", e.getErrorMessage());
			assertEquals(400, e.getErrorCode());
		}
	}

	@Test
	void shouldSaveAVote_WhenValidVoteIsGiven() throws HandledException {

		Movie movie = createMovie();
		
		Float movieVote = (float) 8;
		
		User user = new User((long) 1, "User", "user@mail.com", new ArrayList<Movie>(), false);
		
		Mockito.when(loggingService.getLoggedUserId(mockedRequest)).thenReturn((long) 1);
		Mockito.when(userRepo.getById(Mockito.anyLong())).thenReturn(user);

		voteService.addVote(movie, movieVote, mockedRequest);
		
		verify(voteRepo, atLeast(1)).save(Mockito.any(Vote.class));
	}

	private Movie createMovie() {

		Movie movie = new Movie(1, "Movie 1", "This is the first movie", "en", "2020-19-05");

		return movie;
	}
}
