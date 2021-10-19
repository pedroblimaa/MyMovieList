package com.myMovieList.controller.dto;

public class MovieApiDto {
	private Long id;
	private String title;
	private String overview;
	private String original_language;
	private float vote_average;
	private Long vote_count;
	private String release_date;
	
	public MovieApiDto() {
		super();
	}
	
	public MovieApiDto(Integer id, String title, String overview, String original_language, double vote_average, Integer vote_count,
			String release_date) {
		super();
		this.id = (long) id;
		this.title = title;
		this.overview = overview;
		this.original_language = original_language;
		this.vote_average = (float) vote_average;
		this.vote_count = (long) vote_count;
		this.release_date = release_date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public float getVote_average() {
		return vote_average;
	}

	public void setVote_average(float vote_average) {
		this.vote_average = vote_average;
	}

	public Long getVote_count() {
		return vote_count;
	}

	public void setVote_count(Long vote_count) {
		this.vote_count = vote_count;
	}

	public String getOriginal_language() {
		return original_language;
	}

	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
}
