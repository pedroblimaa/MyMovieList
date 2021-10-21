package com.myMovieList.controller.dto;

import javax.validation.constraints.NotNull;

public class VoteAddDto {

	@NotNull
	private Long movieId;
	@NotNull
	private Float vote;

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Float getVote() {
		return vote;
	}

	public void setVote(Float vote) {
		this.vote = vote;
	}
}
