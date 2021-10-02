package com.mtrippricer.model;

import com.mtrippricer.dto.AttractionDto;
import com.mtrippricer.dto.VisitedLocationDto;
import lombok.Getter;
import lombok.ToString;

@Getter
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
