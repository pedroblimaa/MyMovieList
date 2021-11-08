package com.myMovieList.integration.karate;

import com.intuit.karate.junit4.Karate;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@RunWith(Karate.class)
@CucumberOptions(features = "classpath:karate/GetMoviesFromApi.feature")
public class GetMoviesFromApiRunner {

}
