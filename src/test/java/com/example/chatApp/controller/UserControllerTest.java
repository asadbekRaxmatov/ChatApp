package com.example.chatApp.controller;


import com.example.chatApp.dto.UserDTO;
import com.example.chatApp.model.User;
import com.example.chatApp.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testAddUser() throws Exception {
        when(userService.addUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"createdAt\": \"2023-05-15T14:30:00\"}"))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDTO> userList = Arrays.asList(userDTO);
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/users/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()").value(1));
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"updateduser\", \"createdAt\": \"2023-05-15T14:30:00\"}"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        when(userService.getUserByUsername(anyString())).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/username/{username}", "testuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testExistsByUsername() throws Exception {
        when(userService.existsByUsername(anyString())).thenReturn(true);

        mockMvc.perform(get("/api/users/exists/{username}", "testuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetUsersByCreatedAt() throws Exception {
        List<UserDTO> userList = Arrays.asList(userDTO);
        when(userService.getUsersByCreatedAt(any(LocalDateTime.class))).thenReturn(userList);

        mockMvc.perform(get("/api/users/created-at/{createdAt}", "2023-05-15T14:30:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()").value(1));
    }

    @Test
    public void testUpdateUsername() throws Exception {
        when(userService.updateUsername(anyLong(), anyString())).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/update-username/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"newusername\""))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testGetUsersByIds() throws Exception {
        List<User> userList = Arrays.asList(user);
        when(userService.getUsersByIds(any(List.class))).thenReturn(userList);

        mockMvc.perform(post("/api/users/get-users-by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1]"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()").value(1));
    }

    @Test
    public void testDeleteAllUsers() throws Exception {
        mockMvc.perform(delete("/api/users/delete-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSearchUsersByUsername() throws Exception {
        List<UserDTO> userList = Arrays.asList(userDTO);
        when(userService.searchUsersByUsername(anyString())).thenReturn(userList);

        mockMvc.perform(get("/api/users/search/{usernameFragment}", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()").value(1));
    }
}

