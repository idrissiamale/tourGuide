package com.mattraction.dto;

import lombok.*;

import java.util.UUID;

/**
 * A DTO which gathers the tourist attraction's data from GpsUtil API. This class inherits from LocationDto in particular its geographic coordinates.
 *
 * @see com.mattraction.dto.LocationDto
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDto extends LocationDto {
    private String attractionName;
    private String city;
    private String state;
    private UUID attractionId;

    public AttractionDto(double latitude, double longitude, String attractionName, String city, String state, UUID attractionId) {
        super(latitude, longitude);
        this.attractionName = attractionName;
        this.city = city;
        this.state = state;
        this.attractionId = attractionId;
    }
}
