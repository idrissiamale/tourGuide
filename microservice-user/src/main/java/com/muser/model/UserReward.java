package com.muser.model;

import com.muser.dto.AttractionDto;
import com.muser.dto.VisitedLocationDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserReward {

	private final VisitedLocationDto visitedLocation;
	private final AttractionDto attraction;
	private int rewardPoints;

	public UserReward(VisitedLocationDto visitedLocation, AttractionDto attraction) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
	}

	public UserReward(VisitedLocationDto visitedLocation, AttractionDto attraction, int rewardPoints) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
		this.rewardPoints = rewardPoints;
	}
}
