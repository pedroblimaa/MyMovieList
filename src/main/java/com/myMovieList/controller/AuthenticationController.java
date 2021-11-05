package com.myMovieList.controller;

import javax.validation.Valid;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.config.security.TokenService;
import com.myMovieList.controller.dto.LoginDto;
import com.myMovieList.controller.dto.TokenDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	@ApiOperation(value = "Route to log into the application")
	@PostMapping
	public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid LoginDto form) throws HandledException {

		UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(form.getEmail(),
				form.getPassword());

		Authentication authentication = authManager.authenticate(loginData);
		String token = tokenService.generateToken(authentication);

		return ResponseEntity.ok(new TokenDto("Bearer", token));

	}
}
