package com.myMovieList.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.myMovieList.config.dto.ErrorHandleDto;
import com.myMovieList.controller.dto.TokenDto;
import com.myMovieList.controller.form.FormLogin;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("dev")
public class AuthTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void shouldGet400WhenTryToLogInWithWrongCredentials() throws JSONException {

		HttpHeaders headers = new HttpHeaders();

		FormLogin userLogin = new FormLogin("dontexist@email.com", "123456");

		HttpEntity<FormLogin> request = new HttpEntity<>(userLogin, headers);

		ResponseEntity<ErrorHandleDto> response = testRestTemplate.postForEntity("/auth", request,
				ErrorHandleDto.class);

		assertEquals(400, response.getBody().getCode());
		assertEquals("Invalid Credentials!", response.getBody().getMessage());
	}

	@Test
	void shouldGetTokenWhenTryToLogInSuccefull() {

		HttpHeaders headers = new HttpHeaders();

		FormLogin userLogin = new FormLogin("mod@mail.com", "123456");

		HttpEntity<FormLogin> request = new HttpEntity<>(userLogin, headers);

		ResponseEntity<TokenDto> response = testRestTemplate.postForEntity("/auth", request, TokenDto.class);

		assertEquals("Bearer", response.getBody().getType());
		assertTrue(!response.getBody().getToken().isEmpty());
	}

}
