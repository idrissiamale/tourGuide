package com.muserlocation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A DTO class which represents a location, a place with its latitude and longitude.
 */
@Getter
@NoArgsConstructor
@ToString
public class LocationDto {
    private double latitude;
    private double longitude;

    public LocationDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
