package com.mattraction.dto;

import lombok.*;

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
