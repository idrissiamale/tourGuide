package com.mtracker;

import com.mtracker.model.User;
import com.mtracker.service.TrackerService;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrackerServiceImplIT {
	@Autowired
	private TrackerService trackerService;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutorGpsUtil;

	@BeforeEach
	public void setUp() {
		Locale.setDefault(Locale.US);
	}

	@Test
	public void highVolumeTrackLocation() throws ExecutionException, InterruptedException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<User> users = trackerService.getAllUsers();
		for(User user : users) {
			trackerService.trackUserLocation(user);
		}

		for(User user : users) {
			while(user.getVisitedLocations().size() < 4) {
				try {
					this.taskExecutorGpsUtil.getThreadPoolExecutor().awaitTermination(100, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
				}
			}
		}


		for(User user : users) {
			assertTrue(user.getVisitedLocations().size() > 0);
		}
		stopWatch.stop();
		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}


}
