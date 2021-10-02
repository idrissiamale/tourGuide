package com.muserlocation.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocationDto {
    private double longitude;
    private double latitude;

    public LocationDto() {
    }

    public LocationDto(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
