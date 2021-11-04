package com.myMovieList.repository;

import java.math.BigInteger;

import com.myMovieList.model.LogMongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogMongoRepository extends MongoRepository<LogMongo, BigInteger>{

}
