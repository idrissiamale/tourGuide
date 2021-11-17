package com.mtrippricer.model;

import com.mtrippricer.dto.AttractionDto;
import com.mtrippricer.dto.VisitedLocationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A model class which gathers user's data about his reward points and the attraction he visited.
 */
@Getter
@NoArgsConstructor
public class UserReward {
	private VisitedLocationDto visitedLocation;
	private AttractionDto attraction;
	private int rewardPoints;
}
