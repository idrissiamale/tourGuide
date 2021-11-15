package com.muser.dto;

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
    private double latitude;
    private double longitude;
}

