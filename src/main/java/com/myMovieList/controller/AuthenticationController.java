package com.myMovieList.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myMovieList.config.dto.ErrorHandleDto;
import com.myMovieList.config.security.TokenService;
import com.myMovieList.controller.dto.LoginDto;
import com.myMovieList.controller.dto.TokenDto;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<?> authenticate(@RequestBody @Valid LoginDto form) {

		UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(form.getEmail(),
				form.getPassword());

		try {
			
			Authentication authentication = authManager.authenticate(loginData);
			String token = tokenService.generateToken(authentication);

			return ResponseEntity.ok(new TokenDto("Bearer", token));
		} catch (AuthenticationException e) {
			return new ResponseEntity<ErrorHandleDto>(new ErrorHandleDto("Invalid Credentials!", 400),
					HttpStatus.BAD_REQUEST);
		}

	}
}
