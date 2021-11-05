package com.myMovieList.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myMovieList.config.dto.ErrorHandleDto;
import com.myMovieList.controller.TheMovieDbApiController;
import com.myMovieList.model.User;
import com.myMovieList.repository.UserRepository;
import com.myMovieList.service.LoggingService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenAuthentication extends OncePerRequestFilter {

	private TokenService tokenService;
	private UserRepository repository;

	@Autowired
	private LoggingService loggingService;


	private static final Log log = LogFactory.getLog(TheMovieDbApiController.class);

	public TokenAuthentication(TokenService tokenService, UserRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recoverToken(request);
		boolean valid = tokenService.tokenIsValid(token);

		try {
			if (valid) {
				authenticateCustomer(token);
			}

			filterChain.doFilter(request, response);
		} catch (Exception e) {

			log.error(loggingService.log("ERROR", "Error with database connection"));

			String jsonString = new ObjectMapper()
					.writeValueAsString(new ErrorHandleDto("Error with database connection", 502));
			PrintWriter out = response.getWriter();
			response.setStatus(HttpStatus.BAD_GATEWAY.value());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(jsonString);
			out.flush();
		}
	}

	private void authenticateCustomer(String token) {
		Long userId = tokenService.getUserId(token);
		User user = repository.findById(userId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.substring(7, token.length());
	}

}
