package com.myMovieList.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import com.myMovieList.config.security.TokenService;

@ActiveProfiles("test")
class LoggingServiceTest {
	
	@Test
	void ShouldReturnTheTokenWhenTheAuthorizationHeaderIsGiven() {
		
		HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
		
		String type = "Bearer";
		String token = "hd2&*Hd782HD7*@hd78@GBd8@&QG";
		
		TokenService tokenService = Mockito.mock(TokenService.class);
		
		Mockito.when(mockedRequest.getHeader("Authorization")).thenReturn(
				type + " " + token);
		Mockito.when(tokenService.getUserId(Mockito.anyString())).thenReturn((long) 1);
		
		LoggingService loggingService = new LoggingService(tokenService);
		
		Long loggedUserId = loggingService.getLoggedUserId(mockedRequest);
		
		assertEquals((long) 1, loggedUserId);
	}
}
