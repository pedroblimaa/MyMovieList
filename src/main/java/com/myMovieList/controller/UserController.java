package com.myMovieList.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.myMovieList.controller.dto.UserDto;
import com.myMovieList.model.User;
import com.myMovieList.repository.UserRepository;
import com.myMovieList.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")

@Validated
@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

	@Autowired
	private AuthService loggingService;

	@Autowired
	private UserRepository userRepo;

	@ApiOperation(value = "Get user info ")
	@GetMapping
	@Transactional
	public ResponseEntity<UserDto> getUserInfo(HttpServletRequest request) {

		Long userId = loggingService.getLoggedUserId(request);

		User user = userRepo.getById(userId);

		return ResponseEntity.ok(new UserDto(user));
	}
}