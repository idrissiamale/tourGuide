package com.mattraction.dto;

import java.util.List;

public class UserInfo {
    private double longitude;
    private double latitude;
    private List<AttractionInfo> nearbyAttractions;

    public UserInfo(double longitude, double latitude, List<AttractionInfo> nearbyAttractions) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.nearbyAttractions = nearbyAttractions;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<AttractionInfo> getNearbyAttractions() {
        return nearbyAttractions;
    }

    public void setNearbyAttractions(List<AttractionInfo> nearbyAttractions) {
        this.nearbyAttractions = nearbyAttractions;
    }
}
