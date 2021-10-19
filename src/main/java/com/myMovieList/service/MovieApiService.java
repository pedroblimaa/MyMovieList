package com.myMovieList.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.controller.dto.MovieApiDto;

@Service
public class MovieApiService {

	@Value("${movies-api.key}")
	String defaultKey;
	@Value("${movies-api.url}")
	String defaultUrl;
	@Value("${movie-search-api.url}")
	String searchUrl;

	public MovieApiService() {
		super();
	}

	public MovieApiService(String defaultKey, String defaultUrl) {
		super();
		this.defaultKey = defaultKey;
		this.defaultUrl = defaultUrl;
	}

	public String mountUrl(String page, String requestString) {
		
		String url = requestString.contains("top_rated") 
				? defaultUrl + requestString + "?"
				: searchUrl + "?query=" + requestString + "&";

		url += "api_key=" + defaultKey;
		
		url += page != null ? "&page=" + page : "";
		
		return url;
	}

	public MovieApiDto getMovieById(Long id) throws HandledException {

		String url = defaultUrl + id + "?api_key=" + defaultKey;

		MovieApiDto movie;

		try {
			RestTemplate restTemplate = new RestTemplate();
			movie = restTemplate.getForObject(url, MovieApiDto.class);
		} catch (Exception e) {
			throw new HandledException("Movie not found", 404);
		}

		return movie;
	}
}