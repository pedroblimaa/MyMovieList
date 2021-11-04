package com.myMovieList.controller;

import com.myMovieList.config.exception.HandledException;
import com.myMovieList.config.validation.ValidateQueryParamsService;
import com.myMovieList.model.LogMongo;
import com.myMovieList.repository.LogMongoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/log", produces = "application/json")
@EnableMongoRepositories(basePackageClasses = LogMongoRepository.class)
public class LogController {
	
	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(TheMovieDbApiController.class);
	
	@Autowired
	private LogMongoRepository logRepo;

	@Autowired
	private ValidateQueryParamsService validateParams;

	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve"),
		@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page"),
		@ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "Sort by a specific field") })
	@ApiOperation(value = "Return the saved logs")
	@GetMapping
	public ResponseEntity<Page<LogMongo>> getLogs(@ApiIgnore Pageable pagination) throws HandledException{

		validateParams.validatePagination(pagination, new LogMongo());
		
		Page<LogMongo> logs = logRepo.findAll(pagination);
		
		log.info("Showing logs");
		
		return ResponseEntity.ok(logs);
	}
}
