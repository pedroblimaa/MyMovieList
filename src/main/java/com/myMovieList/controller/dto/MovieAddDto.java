package com.myMovieList.controller.dto;

import javax.validation.constraints.NotNull;

public class MovieAddDto {

	@NotNull
	private Long id;
	private Float vote;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getVote() {
		return vote;
	}

	public void setVote(Float vote) {
		this.vote = vote;
	}
}