package com.myMovieList.controller;

import java.util.List;

import javax.validation.constraints.Min;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.controller.dto.MovieApiDto;
import com.myMovieList.controller.dto.MovieApiPageDto;
import com.myMovieList.service.LoggingService;
import com.myMovieList.service.MovieApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping(value = "/movies", produces = "application/json")
public class TheMovieDbApiController {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(TheMovieDbApiController.class);

	@Autowired
	private MovieApiService apiService;

	@Autowired
	private LoggingService loggingService;

	@ApiOperation(value = "Return movies from the external API")
	@Cacheable(value = "apiMovies")
	@GetMapping
	public ResponseEntity<List<MovieApiDto>> listTopRated(@RequestParam(defaultValue = "1") @Min(1) Integer page,
			@RequestParam(required = false) String name) throws HandledException {

		MovieApiPageDto movies;

		String url = name != null ? apiService.mountUrl(null, name)
				: apiService.mountUrl(String.valueOf(page), "top_rated");

		try {
			RestTemplate restTemplate = new RestTemplate();
			movies = restTemplate.getForObject(url, MovieApiPageDto.class);
		} catch (ResourceAccessException e) {
			log.error(loggingService.log("ERROR", "Error on access to API: " + e.getMessage()));
			throw new HandledException("Movie search API is unavailable", 502);
		}

		String loggingMessage = loggingService.log("INFO",
				name != null ? "Caching request with name: " + name : "Caching top_rated movies request");
		log.info(loggingMessage);

		if(movies == null) {
			log.error(loggingService.log("ERROR", "Error on access to API: movies is null"));
			throw new HandledException("Movie search is unavailable", 502);
		}
		
		return ResponseEntity.ok(movies.getResults());
	}
}
