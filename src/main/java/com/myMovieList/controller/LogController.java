package com.myMovieList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myMovieList.model.LogMongo;
import com.myMovieList.repository.LogMongoRepository;

@RestController
@RequestMapping("/log")
@EnableMongoRepositories(basePackageClasses = LogMongoRepository.class)
public class LogController {
	
	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(TheMovieDbApiController.class);
	
	@Autowired
	private LogMongoRepository logRepo;

	@GetMapping
	public ResponseEntity<?> getLogs(Pageable pagination){
		
		Page<LogMongo> logs = logRepo.findAll(pagination);
		
		log.info("Showing logs");
		
		return ResponseEntity.ok(logs);
	}
}
