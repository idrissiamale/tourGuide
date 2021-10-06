package com.mattraction.dto;

public class AttractionInfo {
    private String name;
    private double latitude;
    private double longitude;
    private double distance;
    private int rewardPoints;

    public AttractionInfo(String name, double latitude, double longitude, double distance, int rewardPoints) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.rewardPoints = rewardPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
