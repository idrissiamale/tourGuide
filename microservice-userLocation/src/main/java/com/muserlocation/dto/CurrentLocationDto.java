package com.muserlocation.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class CurrentLocationDto {
    private UUID userId;
    private LocationDto locationDto;

    public CurrentLocationDto() {
    }

    public CurrentLocationDto(UUID userId, LocationDto locationDto) {
        this.userId = userId;
        this.locationDto = locationDto;
    }
}
