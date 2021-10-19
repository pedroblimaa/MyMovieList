package com.myMovieList.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.myMovieList.controller.dto.TokenDto;
import com.myMovieList.controller.dto.UserDto;
import com.myMovieList.controller.form.FormLogin;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MovieListTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	private RestTemplate patchRestTemplate;

	private HttpHeaders headers;

	@BeforeEach
	public void setUp() {

		this.patchRestTemplate = testRestTemplate.getRestTemplate();
		HttpClient httpClient = HttpClientBuilder.create().build();
		this.patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
	}

	@Test
	public void shouldListTwoMoviesWhenRequestedTheFirstUser() {

		headers = new HttpHeaders();

		String authHeader = getAuthHeader();

		headers.add("Authorization", authHeader);

		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = testRestTemplate.exchange("/movie-list", HttpMethod.GET, request,
				String.class);

		assertTrue(response.getBody().contains("Movie 1"));
		assertTrue(response.getBody().contains("Movie 2"));
	}

	@Test
	public void shouldListEmptyMoviesWhenRequestedTheSecondUser() {

		headers = new HttpHeaders();

		String authHeader = getAuthHeaderUser2();

		headers.add("Authorization", authHeader);

		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = testRestTemplate.exchange("/movie-list", HttpMethod.GET, request,
				String.class);

		assertFalse(response.getBody().contains("Movie 1"));
		assertTrue(response.getBody().contains("[null]"));
	}

	@Test
	public void shouldReturnTrueWhenTryToPutTheListPrivate() throws JSONException {

		headers = new HttpHeaders();

		String authHeader = getAuthHeader();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", authHeader);

		JSONObject json = new JSONObject();
		json.put("privateList", true);

		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

		UserDto response = patchRestTemplate.patchForObject("/movie-list/private-list", request, UserDto.class);

		assertEquals("mod@mail.com", response.getEmail());
		assertTrue(response.getPrivateList());
	}

	@Sql("/test.sql")
	@Test
	public void shouldReturnFalseWhenTryToRemoveTheListPrivate() throws JSONException {

		headers = new HttpHeaders();

		String authHeader = getAuthHeader();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", authHeader);

		JSONObject json = new JSONObject();
		json.put("privateList", false);

		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

		UserDto response = patchRestTemplate.patchForObject("/movie-list/private-list", request, UserDto.class);

		assertEquals("mod@mail.com", response.getEmail());
		assertFalse(response.getPrivateList());
	}

	private String getAuthHeader() {

		FormLogin userLogin = new FormLogin("mod@mail.com", "123456");

		HttpEntity<FormLogin> request = new HttpEntity<>(userLogin, headers);

		ResponseEntity<TokenDto> response = testRestTemplate.postForEntity("/auth", request, TokenDto.class);

		String token = response.getBody().getType() + " " + response.getBody().getToken();

		return token;
	}

	private String getAuthHeaderUser2() {

		FormLogin userLogin = new FormLogin("person@mail.com", "123456");

		HttpEntity<FormLogin> request = new HttpEntity<>(userLogin, headers);

		ResponseEntity<TokenDto> response = testRestTemplate.postForEntity("/auth", request, TokenDto.class);

		String token = response.getBody().getType() + " " + response.getBody().getToken();

		return token;
	}
}