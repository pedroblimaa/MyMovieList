package com.myMovieList.controller.dto;

import javax.validation.constraints.NotNull;

public class MovieIdDto {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
