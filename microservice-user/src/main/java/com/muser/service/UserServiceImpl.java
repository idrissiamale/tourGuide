package com.muser.service;

import com.muser.dto.LocationDto;
import com.muser.dto.VisitedLocationDto;
import com.muser.helper.InternalTestHelper;
import com.muser.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * Implementation of the UserService interface.
 *
 * @see com.muser.service.UserService
 */
@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    boolean testMode = true;

    public UserServiceImpl() {
        if (testMode) {
            logger.info("TestMode enabled");
            logger.debug("Initializing users");
            initializeInternalUsers();
            logger.debug("Finished initializing users");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public User getUser(String userName) {
        logger.info("Successfully fetched the user with the following name : " + userName);
        return internalUserMap.get(userName);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<User> getAllUsers() {
        logger.info("The users have been successfully fetched");
        return new CopyOnWriteArrayList<>(internalUserMap.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUser(User user) {
        logger.info("Successfully added the user with the following name : " + user.getUserName());
        if (!internalUserMap.containsKey(user.getUserName())) {
            internalUserMap.put(user.getUserName(), user);
        }
    }


    /**********************************************************************************
     *
     * Methods Below: For Internal Testing
     *
     **********************************************************************************/

    private final Map<String, User> internalUserMap = new ConcurrentHashMap<>();

    /**
     * Initializing internal users provided and stored in memory for testing purposes.
     */
    private void initializeInternalUsers() {
        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            User user = new User(UUID.randomUUID(), userName, phone, email);
            generateUserLocationHistory(user);

            internalUserMap.put(userName, user);
        });
        logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }

    /**
     * Generating user locations history for testing purposes.
     */
    private void generateUserLocationHistory(User user) {
        IntStream.range(0, 3).forEach(i -> {
            user.addToVisitedLocations(new VisitedLocationDto(user.getUserId(), new LocationDto(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
        });
    }

    /**
     * Generating a random longitude for testing purposes.
     *
     * @return random longitude.
     */
    private double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    /**
     * Generating a random latitude for testing purposes.
     *
     * @return random latitude.
     */
    private double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    /**
     * Generating a random time for testing purposes.
     *
     * @return local date time.
     */
    private Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }
}
