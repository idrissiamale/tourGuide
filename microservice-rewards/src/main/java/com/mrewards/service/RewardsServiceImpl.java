package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.LocationDto;
import com.mrewards.dto.VisitedLocationDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;
import com.mrewards.proxies.MicroserviceAttractionProxy;
import com.mrewards.proxies.MicroserviceUsersProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the RewardsService interface.
 *
 * @see com.mrewards.service.RewardsService
 */
@Service
public class RewardsServiceImpl implements RewardsService {
    private Logger logger = LoggerFactory.getLogger(RewardsServiceImpl.class);
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    @Autowired
    private RewardCentral rewardCentral;

    @Autowired
    MicroserviceAttractionProxy microserviceAttractionProxy;

    @Autowired
    MicroserviceUsersProxy microserviceUsersProxy;

    public RewardsServiceImpl(RewardCentral rewardCentral, MicroserviceAttractionProxy microserviceAttractionProxy, MicroserviceUsersProxy microserviceUsersProxy) {
        this.rewardCentral = rewardCentral;
        this.microserviceAttractionProxy = microserviceAttractionProxy;
        this.microserviceUsersProxy = microserviceUsersProxy;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Async(value = "taskExecutorRewardsPoint")
    @Override
    public CompletableFuture<Void> calculateRewards(User user) {
        List<VisitedLocationDto> userLocations = user.getVisitedLocations();
        CopyOnWriteArrayList<AttractionDto> attractions = getAllAttractions();
        for (VisitedLocationDto visitedLocation : userLocations) {
            for (AttractionDto attraction : attractions) {
                if (hasNoReward(user, attraction) && nearAttraction(visitedLocation, attraction)) {
                    addRewardPoints(user, attraction, visitedLocation);
                }
            }
        }
        logger.info("The rewards have been successfully calculated for the following user : " + user.getUserName());
        return CompletableFuture.completedFuture(null);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<UserReward> getUserRewards(User user) {
        logger.info("Successfully fetched rewards for the following user : " + user.getUserName());
        return user.getUserRewards();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public User getUser(String userName) {
        logger.info("Successfully fetched the user with the following name : " + userName);
        return microserviceUsersProxy.getUser(userName);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<User> getAllUsers() {
        logger.info("The users have been successfully fetched");
        return microserviceUsersProxy.getAllUsers();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<AttractionDto> getAllAttractions() {
        logger.info("The attractions have been successfully fetched");
        return microserviceAttractionProxy.getAllAttractions();
    }

    /**
     * Submitting reward points to the user.
     *
     * @param user,            it refers to the registered user.
     * @param attraction,      it refers to the tourist attraction visited by the user.
     * @param visitedLocation, it refers to the place visited by the user.
     */
    private void addRewardPoints(User user, AttractionDto attraction, VisitedLocationDto visitedLocation) {
        int rewardPoints = rewardCentral.getAttractionRewardPoints(user.getUserId(), attraction.getAttractionId());
        user.addUserReward(new UserReward(visitedLocation, attraction, rewardPoints));
    }

    /**
     * Checking if the user whether received or not the reward points for the following attraction.
     *
     * @param user,       it refers to the registered user.
     * @param attraction, it refers to the tourist attraction visited by the user.
     * @return true if the user has not yet received the reward points.
     */
    private boolean hasNoReward(User user, AttractionDto attraction) {
        return user.getUserRewards().stream()
                .noneMatch(reward -> reward.getAttraction().getAttractionName().equals(attraction.getAttractionName()));
    }

    /**
     * Checking if the user whether visited or not the following attraction by checking if the user is near the attraction.
     * The proximity is in miles.
     *
     * @param visitedLocation, it refers to the place visited by the user.
     * @param attraction,      it refers to the tourist attraction visited by the user.
     * @return true if the user is near the attraction.
     */
    private boolean nearAttraction(VisitedLocationDto visitedLocation, AttractionDto attraction) {
        int proximityBuffer = 10;
        return !(getDistance(attraction, visitedLocation.getLocationDto()) > proximityBuffer);
    }

    /**
     * Method which calculates the distance between two locations. The formula used here to find the distance between them is The Haversine Formula.
     *
     * @param loc1, latitude/longitude of the first location.
     * @param loc2, latitude/longitude of the second location compared with the first to get the distance between them.
     * @return the distance between two locations in miles.
     */
    private double getDistance(LocationDto loc1, LocationDto loc2) {
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }
}

