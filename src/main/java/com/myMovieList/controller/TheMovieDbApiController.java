package com.myMovieList.controller;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.myMovieList.config.dto.ErrorHandleDto;
import com.myMovieList.config.exception.HandledException;
import com.myMovieList.controller.dto.MovieApiPageDto;
import com.myMovieList.service.LoggingService;
import com.myMovieList.service.MovieApiService;

@RestController
@Validated
@RequestMapping("/movies")
public class TheMovieDbApiController {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(TheMovieDbApiController.class);

	@Autowired
	private MovieApiService apiService;
	
	@Autowired
	private LoggingService loggingService;

	@Cacheable(value = "apiMovies")
	@GetMapping
	public ResponseEntity<?> listTopRated(@RequestParam(defaultValue = "1") @Min(1) Integer page,
			@RequestParam(required = false) String name) throws HandledException {

		MovieApiPageDto movies;

		String url = name != null ?  apiService.mountUrl(null, name)
				: apiService.mountUrl(String.valueOf(page), "top_rated");

		try {
			RestTemplate restTemplate = new RestTemplate();
			movies = restTemplate.getForObject(url, MovieApiPageDto.class);
		} catch (Exception e) {
			return new ResponseEntity<ErrorHandleDto>(new ErrorHandleDto(e.getLocalizedMessage().toUpperCase(), 404),
					HttpStatus.NOT_FOUND);
		}

		String loggingMessage = loggingService.log("INFO", name != null ? "Caching request with name: " + name : "Caching top_rated movies request");
		log.info(loggingMessage);

		return ResponseEntity.ok(movies.getResults());
	}
}
