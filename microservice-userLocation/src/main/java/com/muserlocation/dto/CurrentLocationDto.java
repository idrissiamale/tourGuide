package com.muserlocation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

/**
 * A DTO class which represents the current user location and for this, gathers the following user's data : his id and location.
 *
 * @see com.muserlocation.dto.LocationDto
 */
@Getter
@NoArgsConstructor
@ToString
public class CurrentLocationDto {
    private UUID userId;
    private LocationDto locationDto;

    public CurrentLocationDto(UUID userId, LocationDto locationDto) {
        this.userId = userId;
        this.locationDto = locationDto;
    }
}
