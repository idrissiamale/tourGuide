package com.mtracker;

import com.mtracker.helper.InternalTestHelper;
import com.mtracker.model.User;
import com.mtracker.proxies.MicroserviceUsersProxy;
import com.mtracker.service.TrackerServiceImpl;
import gpsUtil.GpsUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPerformance {

	private GpsUtil gpsUtil;
	private TrackerServiceImpl trackerServiceImpl;
	private User user;

	@Mock
	MicroserviceUsersProxy microserviceUsersProxy;

	@BeforeEach
	public void setUp() {
		Locale.setDefault(Locale.US);
		gpsUtil = new GpsUtil();
		trackerServiceImpl = new TrackerServiceImpl(gpsUtil, userService, rewardsCentralService);
		user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
	}

	/*
	 * A note on performance improvements:
	 *     
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *     
	 *     		InternalTestHelper.setInternalUserNumber(100000);
	 *     
	 *     
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent. 
	 * 
	 *     These are performance metrics that we are trying to hit:
	 *     
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */

	@Test
	public void highVolumeTrackLocation() {
		GpsUtil gpsUtil = new GpsUtil();
		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		InternalTestHelper.setInternalUserNumber(100);
		TrackerServiceImpl trackerService = new TrackerServiceImpl(gpsUtil, userService, rewardsCentralService);

		List<User> allUsers = new ArrayList<>();
		allUsers = trackerService.getAllUsers();
		
	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for(User user : allUsers) {
			trackerService.trackUserLocation(user);
		}
		stopWatch.stop();
		//trackerService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}


}
