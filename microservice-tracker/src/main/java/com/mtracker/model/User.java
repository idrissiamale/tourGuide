package com.mtracker.model;

import com.mtracker.dto.VisitedLocationDto;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User model class with id, name, phone number, email, and the locations he visited as fields.
 */
@Getter
@ToString
public class User {
	private final UUID userId;
	private final String userName;
	private String phoneNumber;
	private String emailAddress;
	private CopyOnWriteArrayList<VisitedLocationDto> visitedLocations = new CopyOnWriteArrayList<>();

	public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public CopyOnWriteArrayList<VisitedLocationDto> getVisitedLocations() {
		return visitedLocations;
	}

	public void addToVisitedLocations(VisitedLocationDto visitedLocation) {
		visitedLocations.add(visitedLocation);
	}
}
