package com.mrewards.service;

import com.mrewards.dto.AttractionDto;
import com.mrewards.dto.VisitedLocationDto;
import com.mrewards.model.User;
import com.mrewards.model.UserReward;
import com.mrewards.proxies.MicroserviceAttractionProxy;
import com.mrewards.proxies.MicroserviceUsersProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rewardCentral.RewardCentral;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceImplTest {
    private RewardsServiceImpl rewardsService;
    private User user;
    private CopyOnWriteArrayList<AttractionDto> attractions = new CopyOnWriteArrayList<>();
    @Mock
    MicroserviceAttractionProxy microserviceAttractionProxy;

    @Mock
    MicroserviceUsersProxy microserviceUsersProxy;

    @BeforeEach
    public void setUp() {
        RewardCentral rewardCentral = new RewardCentral();
        rewardsService = new RewardsServiceImpl(rewardCentral, microserviceAttractionProxy, microserviceUsersProxy);
        user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        attractions.add(new AttractionDto("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
        attractions.add(new AttractionDto("Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D));
        attractions.add(new AttractionDto("Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
        attractions.add(new AttractionDto("Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
        attractions.add(new AttractionDto("Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
        attractions.add(new AttractionDto("Hot Springs National Park", "Hot Springs", "AR", 34.52153D, -93.042267D));
        attractions.add(new AttractionDto("Kartchner Caverns State Park", "Benson", "AZ", 31.837551D, -110.347382D));
        attractions.add(new AttractionDto("Legend Valley", "Thornville", "OH", 39.937778D, -82.40667D));
        attractions.add(new AttractionDto("Flowers Bakery of London", "Flowers Bakery of London", "KY", 37.131527D, -84.07486D));
        attractions.add(new AttractionDto("McKinley Tower", "Anchorage", "AK", 61.218887D, -149.877502D));
        attractions.add(new AttractionDto("Flatiron Building", "New York City", "NY", 40.741112D, -73.989723D));
        attractions.add(new AttractionDto("Fallingwater", "Mill Run", "PA", 39.906113D, -79.468056D));
        attractions.add(new AttractionDto("Union Station", "Washington D.C.", "CA", 38.897095D, -77.006332D));
        attractions.add(new AttractionDto("Roger Dean Stadium", "Jupiter", "FL", 26.890959D, -80.116577D));
        attractions.add(new AttractionDto("Texas Memorial Stadium", "Austin", "TX", 30.283682D, -97.732536D));
        attractions.add(new AttractionDto("Bryant-Denny Stadium", "Tuscaloosa", "AL", 33.208973D, -87.550438D));
        attractions.add(new AttractionDto("Tiger Stadium", "Baton Rouge", "LA", 30.412035D, -91.183815D));
        attractions.add(new AttractionDto("Neyland Stadium", "Knoxville", "TN", 35.955013D, -83.925011D));
        attractions.add(new AttractionDto("Kyle Field", "College Station", "TX", 30.61025D, -96.339844D));
        attractions.add(new AttractionDto("San Diego Zoo", "San Diego", "CA", 32.735317D, -117.149048D));
        attractions.add(new AttractionDto("Zoo Tampa at Lowry Park", "Tampa", "FL", 28.012804D, -82.469269D));
        attractions.add(new AttractionDto("Franklin Park Zoo", "Boston", "MA", 42.302601D, -71.086731D));
        attractions.add(new AttractionDto("El Paso Zoo", "El Paso", "TX", 31.769125D, -106.44487D));
        attractions.add(new AttractionDto("Kansas City Zoo", "Kansas City", "MO", 39.007504D, -94.529625D));
        attractions.add(new AttractionDto("Bronx Zoo", "Bronx", "NY", 40.852905D, -73.872971D));
        attractions.add(new AttractionDto("Cinderella Castle", "Orlando", "FL", 28.419411D, -81.5812D));
    }

    @Test
    @DisplayName("Testing that calculateRewards is working well by checking that the user has received his rewards after visiting a tourist attraction")
    public void shouldGetUserRewards() {
        AttractionDto attractionDto = new AttractionDto("Disneyland", "Anaheim", "CA", 33.817595, -117.922008);
        when(microserviceAttractionProxy.getAllAttractions()).thenReturn(attractions);
        user.addToVisitedLocations(new VisitedLocationDto(user.getUserId(), attractionDto, new Date()));

        rewardsService.calculateRewards(user);


        List<UserReward> userRewards = user.getUserRewards();

        assertEquals(1, userRewards.size());
    }
}
