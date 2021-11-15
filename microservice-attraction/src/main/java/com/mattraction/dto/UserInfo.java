package com.mattraction.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * A DTO which gathers the location of the user (latitude/longitude) and the closest five tourist attractions to him.
 *
 * @see com.mattraction.dto.AttractionInfo
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserInfo {
    private double latitude;
    private double longitude;
    private List<AttractionInfo> nearbyAttractions;

    public UserInfo(double latitude, double longitude, List<AttractionInfo> nearbyAttractions) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.nearbyAttractions = nearbyAttractions;
    }
}
