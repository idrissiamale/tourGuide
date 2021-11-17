package com.mtrippricer.dto;

import lombok.*;

import java.util.UUID;

/**
 * A DTO which gathers the tourist attraction's data from GpsUtil API. This class inherits from LocationDto in particular its geographic coordinates.
 *
 * @see com.mtrippricer.dto.LocationDto
 */

@NoArgsConstructor
public class AttractionDto extends LocationDto {
    private String attractionName;
    private String city;
    private String state;
    private UUID attractionId;
}
