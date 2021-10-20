package com.myMovieList.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.controller.dto.MovieApiDto;
import com.myMovieList.model.Movie;
import com.myMovieList.model.User;
import com.myMovieList.repository.MovieRepository;
import com.myMovieList.repository.UserRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepo;

	@Autowired
	private AuthService loggingService;

	@Autowired
	private UserRepository userRepo;

	public MovieService() {
		super();
	}

	public MovieService(MovieRepository movieRepo) {
		super();
		this.movieRepo = movieRepo;
	}

	public MovieService(AuthService loggingService, UserRepository userRepo) {
		super();
		this.loggingService = loggingService;
		this.userRepo = userRepo;
	}

	public Movie getOrCreateMovie(String title, MovieApiDto apiMovie) {

		Optional<Movie> movieOptional = movieRepo.findByName(title);

		Movie movie;

		if (movieOptional.isPresent()) {
			return movieOptional.get();
		}
		
		movie = new Movie(apiMovie);

		movieRepo.save(movie);

		return movie;
	}

	public void verifyMovieInTheList(String movieTitle, HttpServletRequest request) throws HandledException {

		Long userId = loggingService.getLoggedUserId(request);

		User user = userRepo.getById(userId);

		List<Movie> movies = user.getMovies();

		for (Movie movie : movies) {
			if (movie.getName().contains(movieTitle)) {
				throw new HandledException("Movie is already in the list", 400);
			}
		}
	}

	@Transactional
	public User updateMovieList(HttpServletRequest request, Movie movie) {

		Long userId = loggingService.getLoggedUserId(request);

		User user = userRepo.getById(userId);

		List<Movie> movies = user.getMovies();

		movies.add(movie);
		user.setMovies(movies);

		return user;
	}

	@Transactional
	public List<Movie> deleteMovie(Long id, User user) {

		List<Movie> movies = user.getMovies();
		
		for (int i = 0; i < movies.size(); i++) {
			if (movies.get(i).getId().equals(id)) {
				movies.remove(i);
			}
		}

		user.setMovies(movies);

		return movies;
	}
}