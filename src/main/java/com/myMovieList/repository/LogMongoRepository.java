package com.myMovieList.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.myMovieList.model.LogMongo;

public interface LogMongoRepository extends MongoRepository<LogMongo, BigInteger>{

}
