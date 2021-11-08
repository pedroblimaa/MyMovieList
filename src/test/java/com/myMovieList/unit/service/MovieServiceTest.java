package com.myMovieList.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.controller.dto.MovieApiDto;
import com.myMovieList.model.Movie;
import com.myMovieList.model.User;
import com.myMovieList.repository.MovieRepository;
import com.myMovieList.repository.UserRepository;
import com.myMovieList.service.AuthService;
import com.myMovieList.service.MovieService;

@DataJpaTest
@ActiveProfiles("test")
class MovieServiceTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private MovieRepository movieRepo;

	private UserRepository userRepo;

	private AuthService loggingService;

	@Test
	void shouldReturnANewMovie_WhenTheMovieDoesntExist() {

		MovieApiDto movieApi = new MovieApiDto(1, "Movie 1", "This is a movie", "en", 7.6, 1400, "2020-04-34");

		MovieService movieService = new MovieService(movieRepo);

		Movie movie = movieService.getOrCreateMovie(movieApi);

		assertEquals(movieApi.getTitle(), movie.getName());
		assertEquals(movieApi.getOverview(), movie.getOverview());
	}

	@Test
	void shouldGetTheMovie_WhenTheMovieExists() {

		MovieApiDto movieApi = new MovieApiDto(1, "Movie 1", "This is a movie", "en", 7.6, 1400, "2020-04-34");
		Movie movieToBeSaved = new Movie(movieApi);
		em.persist(movieToBeSaved);

		MovieService movieService = new MovieService(movieRepo);

		Movie movie = movieService.getOrCreateMovie(movieApi);

		assertEquals(movieApi.getTitle(), movie.getName());
		assertEquals(movieApi.getOverview(), movie.getOverview());
	}

	@Test
	void shouldThrowException_WhenTheGivenMovieExistsOnTheList() {

		HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

		User user = createUser();

		loggingService = Mockito.mock(AuthService.class);
		userRepo = Mockito.mock(UserRepository.class);

		MovieService movieService = new MovieService(loggingService, userRepo);

		Mockito.when(loggingService.getLoggedUserId(Mockito.any())).thenReturn((long) 1);
		Mockito.when(userRepo.getById(Mockito.anyLong())).thenReturn(user);

		try {
			movieService.verifyMovieInTheList("Movie 1", mockedRequest);
			assertTrue(false);
		} catch (HandledException e) {
			assertEquals(400, e.getErrorCode());
			assertEquals("Movie is already in the list", e.getErrorMessage());

		}
	}

	@Test
	void shouldNotThrowException_WhenTheGivenMovieDoesNotExistsInTheList() {

		HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

		User user = createUser();

		loggingService = Mockito.mock(AuthService.class);
		userRepo = Mockito.mock(UserRepository.class);

		MovieService movieService = new MovieService(loggingService, userRepo);

		Mockito.when(loggingService.getLoggedUserId(Mockito.any())).thenReturn((long) 1);
		Mockito.when(userRepo.getById(Mockito.anyLong())).thenReturn(user);

		try {
			movieService.verifyMovieInTheList("Movie 3", mockedRequest);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	void shouldReturnAUserWithANewMovie_WhenAMovieIsGiven() {

		HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

		User user = createUser();
		Movie movie = new Movie(3, "Movie 3", "This is the 3rd movie", "en", "2021-01-03");

		loggingService = Mockito.mock(AuthService.class);
		userRepo = Mockito.mock(UserRepository.class);

		MovieService movieService = new MovieService(loggingService, userRepo);

		Mockito.when(loggingService.getLoggedUserId(Mockito.any())).thenReturn((long) 1);
		Mockito.when(userRepo.getById(Mockito.anyLong())).thenReturn(user);

		User updatedUser = movieService.updateMovieList(mockedRequest, movie);

		assertTrue(updatedUser.getMovies().contains(movie));
	}

	@Test
	void shouldThrowAnException_WhenAUserIsNotLogged() {
		HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

		Movie movie = new Movie(3, "Movie 3", "This is the 3rd movie", "en", "2021-01-03");

		loggingService = Mockito.mock(AuthService.class);
		userRepo = Mockito.mock(UserRepository.class);

		MovieService movieService = new MovieService(loggingService, userRepo);

		try {
			movieService.updateMovieList(mockedRequest, movie);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(e.toString().contains("NullPointerException"));
		}
	}

	private User createUser() {

		List<Movie> movies = new ArrayList<>();

		movies.add(new Movie(1, "Movie 1", "This is a new Movie 1", "en", "2020-12-09"));
		movies.add(new Movie(2, "Movie 2", "This is a new Movie 2", "en", "2020-08-20"));

		User user = new User((long) 1, "Mod", "mod@mail.com", movies, true);

		return user;
	}

}
