package com.myMovieList.controller.dto;

import java.util.List;

public class MovieApiPageDto {
	
	private String pages;
	private List<MovieApiDto> results;

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public List<MovieApiDto> getResults() {
		return results;
	}

	public void setResults(List<MovieApiDto> results) {
		this.results = results;
	}
}
