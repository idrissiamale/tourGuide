package com.muserlocation.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@ToString
public class VisitedLocationDto {
    private UUID userId;
    private LocationDto locationDto;
    private Date timeVisited;

    public VisitedLocationDto() {
    }

    public VisitedLocationDto(UUID userId, LocationDto locationDto, Date timeVisited) {
        this.userId = userId;
        this.locationDto = locationDto;
        this.timeVisited = timeVisited;
    }
}
