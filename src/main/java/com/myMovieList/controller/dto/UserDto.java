package com.myMovieList.controller.dto;

import com.myMovieList.model.User;

public class UserDto {

	private String name;
	private String email;
	private Boolean privateList;
	
	public UserDto() {
		super();
	}

	public UserDto(User user) {
		super();
		this.name = user.getName();
		this.email = user.getEmail();
		this.privateList = user.getPrivateList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getPrivateList() {
		return privateList;
	}

	public void setPrivateList(Boolean privateList) {
		this.privateList = privateList;
	}

}
