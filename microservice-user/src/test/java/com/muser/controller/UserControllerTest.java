package com.muser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muser.model.User;
import com.muser.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setUpPerTest() {
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Checking that the controller returns status code 200 when the user is found by his username")
    public void shouldReturn200WhenUserExists() throws Exception {
        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        String jsonContent = mapper.writeValueAsString(user);
        when(userService.getUser(user.getUserName())).thenReturn(user);

        mockMvc
                .perform(get("/getUser")
                        .param("userName", "jon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(userService).getUser(user.getUserName());
    }


    @Test
    @DisplayName("Checking that the controller returns status code 200 when all the users are fetched")
    public void shouldReturn200WhenGetAllUsers() throws Exception {
        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
        CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
        users.add(user);
        users.add(user2);
        String jsonContent = mapper.writeValueAsString(users);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc
                .perform(get("/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }
}
