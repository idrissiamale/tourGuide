package com.mattraction.dto;

import lombok.*;

/**
 * A DTO which gathers :
 * - the name of Tourist attraction,
 * - Tourist attractions latitude/longitude,
 * - The distance in miles between the user's location and each of the attractions.
 * - The reward points for visiting each Attraction.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttractionInfo {
    private String name;
    private double latitude;
    private double longitude;
    private double distance;
    private int rewardPoints;

    public AttractionInfo(String name, double latitude, double longitude, double distance, int rewardPoints) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.rewardPoints = rewardPoints;
    }
}
