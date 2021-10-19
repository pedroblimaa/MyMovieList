package com.myMovieList.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myMovieList.config.security.TokenService;
import com.myMovieList.model.User;
import com.myMovieList.repository.UserRepository;

@Service
public class LoggingService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepo;

	public LoggingService(TokenService tokenService) {
		super();
		this.tokenService = tokenService;
	}

	public User getUserByRequest(HttpServletRequest request) {

		Long userId = getLoggedUserId(request);

		User user = userRepo.getById(userId);

		return user;
	}

	public Long getLoggedUserId(HttpServletRequest request) {

		String token = request.getHeader("Authorization");

		String formattedToken = token.substring(7, token.length());

		Long userId = tokenService.getUserId(formattedToken);

		return userId;
	}
}