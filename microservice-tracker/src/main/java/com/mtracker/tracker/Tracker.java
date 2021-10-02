package com.mtracker.tracker;

import com.mtracker.model.User;
import com.mtracker.service.TrackerService;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class Tracker {
    private Logger logger = LoggerFactory.getLogger(Tracker.class);

    @Autowired
    private TrackerService trackerService;

    public Tracker(TrackerService trackerService) {
        this.trackerService = trackerService;
    }


    //@Async(value = "taskExecutor")
    public void trackAllUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //CompletableFuture<List<User>> getAllUsers = trackerService.getAllUsers();
        List<User> userList = trackerService.getAllUsers();
        logger.info("Start Tracking " + userList.size() + "users");
        userList.parallelStream().forEach(u ->
        {
            try {
                trackerService.trackUserLocation(u);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        stopWatch.stop();
        logger.info("Complete Task 1, time-consuming:" + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
    }
}
