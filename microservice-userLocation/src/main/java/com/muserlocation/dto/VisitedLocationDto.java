package com.muserlocation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

/**
 * A DTO class which gathers the following user's data : his id, the visited location and the time when he visited it.
 *
 * @see com.muserlocation.dto.LocationDto
 */
@Getter
@NoArgsConstructor
@ToString
public class VisitedLocationDto {
    private UUID userId;
    private LocationDto locationDto;
    private Date timeVisited;

    public VisitedLocationDto(UUID userId, LocationDto locationDto, Date timeVisited) {
        this.userId = userId;
        this.locationDto = locationDto;
        this.timeVisited = timeVisited;
    }
}
