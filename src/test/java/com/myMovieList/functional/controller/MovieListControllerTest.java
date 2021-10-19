package com.myMovieList.functional.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.myMovieList.model.Movie;
import com.myMovieList.model.User;
import com.myMovieList.service.LoggingService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MovieListControllerTest {

	@MockBean
	private LoggingService loggingService;

	@Autowired
	private MockMvc mockMvc;

	private URI uri;

	@BeforeEach
	void beforeEach() throws URISyntaxException {
		uri = new URI("/movie-list");
	}

	@Test
	void shouldReturnSmallerList_WhenARequestIsSendToDelete() throws Exception {

		User user = createUser();

		JSONObject json = new JSONObject();
		json.put("id", 1);

		Mockito.when(loggingService.getUserByRequest(Mockito.any())).thenReturn(user);

		this.mockMvc
				.perform(MockMvcRequestBuilders.delete(uri).content(json.toString())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)));
	}
	
	@Test
	void shouldReturnTheSameList_WhenTryToDeleteAMovieThatDoesntExist() throws Exception {

		User user = createUser();

		JSONObject json = new JSONObject();
		json.put("id", 5);

		Mockito.when(loggingService.getUserByRequest(Mockito.any())).thenReturn(user);

		this.mockMvc
				.perform(MockMvcRequestBuilders.delete(uri).content(json.toString())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)));
	}

	@Test
	void shouldReturnEmptyList_WhenRequestIsMadeToUserWithNoMovies() throws Exception {

		Mockito.when(loggingService.getLoggedUserId(Mockito.any())).thenReturn((long) 2);

		this.mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", Matchers.is(0)));
	}

	@Test
	@Sql("/test.sql")
	void shouldListMovies_WhenRequestIsMadeToUserWithMovies() throws Exception {

		Mockito.when(loggingService.getLoggedUserId(Mockito.any())).thenReturn((long) 1);

		this.mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", Matchers.is(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].name").value("Movie 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.[1].name").value("Movie 2"));
	}

	private User createUser() {

		List<Movie> movies = new ArrayList<>();

		movies.add(new Movie(1, "Movie 1", "This is a new Movie 1", "en", "2020-12-09"));
		movies.add(new Movie(2, "Movie 2", "This is a new Movie 2", "en", "2020-08-20"));

		User user = new User((long) 1, "Mod", "mod@mail.com", movies, true);

		return user;
	}

}