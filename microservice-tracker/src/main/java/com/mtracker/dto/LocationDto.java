package com.mtracker.dto;

import lombok.*;

/**
 * A DTO class which represents a location, a place with its latitude and longitude.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private double longitude;
    private double latitude;
}
