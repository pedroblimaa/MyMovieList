package com.myMovieList.unit.service;

import static org.junit.jupiter.api.Assertions.*;

import com.myMovieList.service.MovieApiService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class MovieApiServiceTest {

	private String defaultUrl;
	private String defaultKey;

	@BeforeEach
	void beforeEach() {
		defaultUrl = "http://alouha.com/";
		defaultKey = "3thisIs2A1Key";
	}

	@Test
	void shouldReturnMountedUrlWhenGivenRequestStringAndPage() {

		MovieApiService apiService = new MovieApiService(defaultKey, defaultUrl);

		String url = apiService.mountUrl("2", "top_rated");

		String expectedUrl = defaultUrl + "top_rated?api_key=" + defaultKey + "&page=2";
		
		assertEquals(expectedUrl, url);

	}
	
	@Test
	void shouldReturnMountedUrlWhenPageIsNotGiven() {

		MovieApiService apiService = new MovieApiService(defaultKey, defaultUrl);

		String url = apiService.mountUrl(null, "top_rated");

		String expectedUrl = defaultUrl + "top_rated?api_key=" + defaultKey;
		
		assertEquals(expectedUrl, url);

	}
}