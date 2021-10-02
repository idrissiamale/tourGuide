package com.mattraction.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocationDto {
    private UUID userId;
    private LocationDto locationDto;
    private Date timeVisited;
}