package com.myMovieList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myMovieList.model.LogMongo;
import com.myMovieList.repository.LogMongoRepository;

@Service
public class LoggingService {
	
	@Autowired
	private LogMongoRepository logRepo;
	
	public String log(String level ,String message) {
		LogMongo logs = new LogMongo(level, message);
		logRepo.save(logs);

		return message;
	}
}
