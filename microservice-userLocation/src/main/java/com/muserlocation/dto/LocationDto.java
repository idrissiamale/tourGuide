package com.muserlocation.dto;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LocationDto {
    private double latitude;
    private double longitude;

    public LocationDto() {
    }

    public LocationDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
