package com.mattraction.dto;

import lombok.*;

import java.util.UUID;

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
