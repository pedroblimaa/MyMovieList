package com.myMovieList.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Movie movie;
	@ManyToOne
	private User user;
	private float voteValue;
	
	public Vote() {
		super();
	}

	public Vote(Movie movie, User user, Float movieVote) {
		super();
		this.movie = movie;
		this.user = user;
		this.voteValue = movieVote;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public float getVoteValue() {
		return voteValue;
	}

	public void setVoteValue(float vote) {
		this.voteValue = vote;
	}

}
