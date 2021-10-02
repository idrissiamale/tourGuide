package com.muserlocation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class CurrentLocationDto {
    private final UUID userId;
    private final LocationDto locationDto;

    public CurrentLocationDto(UUID userId, LocationDto locationDto) {
        this.userId = userId;
        this.locationDto = locationDto;
    }
}
