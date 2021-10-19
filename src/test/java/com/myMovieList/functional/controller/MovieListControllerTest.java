package com.myMovieList.functional.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.myMovieList.model.Movie;
import com.myMovieList.model.User;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MovieListControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private URI uri;

	@BeforeEach
	void beforeEach() throws URISyntaxException {
		uri = new URI("/movie-list");
	}

	@Test
	void shouldListMoviesWhenRequestIsMade() throws Exception {

		List<Movie> movies = createMovies();

		this.mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", Matchers.is(0)));

	}

	private User createUser(List<Movie> movies) {

		User user = new User((long) 1, "Mod", "mod@mail.com", movies, true);

		return user;
	}

	private List<Movie> createMovies() {
		List<Movie> movies = new ArrayList<>();

		movies.add(new Movie("Movie 1", "This is a new Movie 1", "en", "2020-12-09"));
		movies.add(new Movie("Movie 2", "This is a new Movie 2", "en", "2020-08-20"));

		return movies;
	}

}
