package com.mrewards.model;

import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.VisitedLocationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A model class which gathers user's data about his reward points and the attraction he visited.
 */
@Getter
@NoArgsConstructor
@ToString
public class UserReward {
    private VisitedLocationDto visitedLocation;
    private AttractionDto attraction;
    private int rewardPoints;

    public UserReward(VisitedLocationDto visitedLocation, AttractionDto attraction, int rewardPoints) {
        this.visitedLocation = visitedLocation;
        this.attraction = attraction;
        this.rewardPoints = rewardPoints;
    }
}
