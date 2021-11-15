package com.mtrippricer.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

/**
 * A DTO class which gathers the following user's data : his id, the visited location and the time when he visited it.
 *
 * @see com.mtrippricer.dto.LocationDto
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocationDto {
    private UUID userId;
    private LocationDto locationDto;
    private Date timeVisited;
}