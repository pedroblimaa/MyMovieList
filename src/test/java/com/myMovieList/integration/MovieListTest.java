package com.myMovieList.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.myMovieList.controller.dto.LoginDto;
import com.myMovieList.controller.dto.MovieApiDto;
import com.myMovieList.controller.dto.TokenDto;
import com.myMovieList.controller.dto.UserDto;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("integration-test")
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
	public void shouldListTwoMovies_WhenRequestedTheFirstUser() {

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
	public void shouldListEmptyMovies_WhenRequestedTheSecondUser() {

		headers = new HttpHeaders();

		String authHeader = getAuthHeaderUser2();

		headers.add("Authorization", authHeader);

		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = testRestTemplate.exchange("/movie-list", HttpMethod.GET, request,
				String.class);

		assertFalse(response.getBody().contains("Movie 1"));
		assertTrue(response.getBody().contains("[]"));
	}

	@Test
	public void shouldReturnTrue_WhenTryToPutTheListPrivate() throws JSONException {

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

	@Test
	public void shouldReturnFalse_WhenTryToRemoveTheListPrivate() throws JSONException {

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

	@Test
	public void shouldReturnMoviesWithTheName_WhenSearchingForItToApiSearch() {

		headers = new HttpHeaders();

		String authHeader = getAuthHeader();

		headers.add("Authorization", authHeader);

		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<MovieApiDto[]> response = testRestTemplate.exchange("/movies?name=Megamind", HttpMethod.GET,
				request, MovieApiDto[].class);

		MovieApiDto[] movies = response.getBody();

		for (MovieApiDto movie : movies) {
			assertTrue(movie.getTitle().contains("Megamind"));
		}
	}

	@ParameterizedTest
	// 1 - sould return empty list _ when searching for a movie that does not exist
	// 2 - should return a full list _ when no parameter is given
	// 3 - should return a full list _ when not existing parameter is given
	@CsvSource({ "?name=Medaminddusiahduia, 0", "'', 20", "?invalidParameter=invalidValue, 20" })
	void shouldReturnANumberOfMovies_WhenSearchByDeterminedParam(String param, int expected) {

		headers = new HttpHeaders();

		String authHeader = getAuthHeader();

		headers.add("Authorization", authHeader);

		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<MovieApiDto[]> response = testRestTemplate.exchange("/movies" + param, HttpMethod.GET, request,
				MovieApiDto[].class);

		MovieApiDto[] movies = response.getBody();

		assertEquals(expected, movies.length);
	}

	@Test
	public void shouldReturnCode400_WhenInvalidPageIsProvidedToApiSearch() {

		headers = new HttpHeaders();

		String authHeader = getAuthHeader();

		headers.add("Authorization", authHeader);

		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = testRestTemplate.exchange("/movies?page=0", HttpMethod.GET, request,
				String.class);

		assertTrue(response.getBody().contains("400"));
	}

	private String getAuthHeader() {

		LoginDto userLogin = new LoginDto("mod@mail.com", "123456");

		HttpEntity<LoginDto> request = new HttpEntity<>(userLogin, headers);

		ResponseEntity<TokenDto> response = testRestTemplate.postForEntity("/auth", request, TokenDto.class);

		System.out.println(response);

		String token = response.getBody().getType() + " " + response.getBody().getToken();

		return token;
	}

	private String getAuthHeaderUser2() {

		LoginDto userLogin = new LoginDto("person@mail.com", "123456");

		HttpEntity<LoginDto> request = new HttpEntity<>(userLogin, headers);

		ResponseEntity<TokenDto> response = testRestTemplate.postForEntity("/auth", request, TokenDto.class);

		String token = response.getBody().getType() + " " + response.getBody().getToken();

		return token;
	}
}