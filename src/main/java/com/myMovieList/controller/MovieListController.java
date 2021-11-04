package com.myMovieList.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.config.validation.ValidateQueryParamsService;
import com.myMovieList.controller.dto.MovieAddDto;
import com.myMovieList.controller.dto.MovieApiDto;
import com.myMovieList.controller.dto.PrivateListDto;
import com.myMovieList.controller.dto.UserDto;
import com.myMovieList.controller.dto.VoteAddDto;
import com.myMovieList.model.Movie;
import com.myMovieList.model.User;
import com.myMovieList.repository.MovieRepository;
import com.myMovieList.repository.UserRepository;
import com.myMovieList.service.AuthService;
import com.myMovieList.service.MovieApiService;
import com.myMovieList.service.MovieService;
import com.myMovieList.service.VoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/movie-list")
public class MovieListController {

	@Autowired
	private AuthService loggingService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private MovieRepository movieRepo;

	@Autowired
	private MovieApiService apiService;

	@Autowired
	private MovieService movieService;

	@Autowired
	private VoteService voteService;

	@Autowired
	private ValidateQueryParamsService validateParams;

	@GetMapping
	public ResponseEntity<Page<Movie>> getList(HttpServletRequest request,
			@PageableDefault(page = 0, size = 10) Pageable pagination) throws HandledException {

		validateParams.validatePagination(pagination, new Movie());

		Long userId = loggingService.getLoggedUserId(request);

		Page<Movie> movies = movieRepo.getMoviesByUserId(userId, pagination);

		return ResponseEntity.ok(movies);
	}

	@GetMapping("/user")
	public ResponseEntity<Page<Movie>> getAnotherList(@RequestParam @Min(1) Long id,
			@PageableDefault(page = 0, size = 10) Pageable pagination) throws HandledException {

		validateParams.validatePagination(pagination, new Movie());

		Optional<User> user = userRepo.findById(id);

		if (!user.isPresent()) {
			throw new HandledException("User not found", 404);
		}

		Boolean privateList = user.get().getPrivateList();

		if (privateList) {
			throw new HandledException("The list is private", 403);
		}

		Page<Movie> movies = movieRepo.getMoviesByUserId(id, pagination);

		return ResponseEntity.ok(movies);
	}

	@PostMapping
	public ResponseEntity<List<Movie>> addMovie(@RequestBody @Valid MovieAddDto form, HttpServletRequest request)
			throws HandledException {

		MovieApiDto apiMovie = apiService.getMovieById(form.getId());

		String movieTitle = apiMovie.getTitle();

		movieService.verifyMovieInTheList(movieTitle, request);

		Movie movie = movieService.getOrCreateMovie(apiMovie);

		voteService.addVote(movie, form.getVote(), request);

		User user = movieService.updateMovieList(request, movie);

		return ResponseEntity.ok(user.getMovies());
	}

	@PatchMapping("/vote")
	public ResponseEntity<Movie> changeNote(@RequestBody @Valid VoteAddDto form, HttpServletRequest request)
			throws HandledException {

		MovieApiDto apiMovie = apiService.getMovieById(form.getMovieId());

		Movie movie = movieService.getOrCreateMovie(apiMovie);

		voteService.addVote(movie, form.getVote(), request);

		return ResponseEntity.ok(movie);
	}

	@PatchMapping("/private-list")
	@Transactional
	public ResponseEntity<UserDto> setPrivateList(HttpServletRequest request, @RequestBody PrivateListDto form) {

		User user = loggingService.getUserByRequest(request);

		user.setPrivateList(form.getPrivateList());

		return ResponseEntity.ok(new UserDto(user));
	}

	@DeleteMapping
	public ResponseEntity<List<Movie>> deleteMovie(@RequestBody @Valid MovieAddDto form, HttpServletRequest request) {

		User user = loggingService.getUserByRequest(request);

		List<Movie> movies = movieService.deleteMovie(form.getId(), user);

		return ResponseEntity.ok(movies);
	}
}