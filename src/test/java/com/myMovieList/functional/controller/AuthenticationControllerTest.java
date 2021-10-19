package com.myMovieList.functional.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	private URI uri;

	@BeforeEach
	void beforeEach() throws URISyntaxException {
		uri = new URI("/auth");
	}

	@Test
	void shouldReturnInvalidCredentialsWhenTryToLogIn() throws Exception {

		String json = "{" + "\"email\": \"aluno@email.com\"," + "\"senha\": \"123456\"" + "}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400));
	}

}
