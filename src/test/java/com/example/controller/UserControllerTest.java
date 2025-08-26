package com.example.controller;

import com.example.model.dto.UserDto;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto testUser1;
    private UserDto testUser2;
    private List<UserDto> allUsers;

    @BeforeEach
    void setUp() {
        testUser1 = UserDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        testUser2 = UserDto.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .build();

        allUsers = Arrays.asList(testUser1, testUser2);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(allUsers);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        when(userService.findById(1L)).thenReturn(testUser1);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        UserDto newUser = UserDto.builder()
                .firstName("New")
                .lastName("User")
                .email("new.user@example.com")
                .password("password123")
                .build();

        UserDto createdUser = UserDto.builder()
                .id(3L)
                .firstName("New")
                .lastName("User")
                .email("new.user@example.com")
                .build();

        when(userService.save(any(UserDto.class))).thenReturn(createdUser);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.firstName", is("New")))
                .andExpect(jsonPath("$.lastName", is("User")));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        UserDto updatedUser = UserDto.builder()
                .id(1L)
                .firstName("Updated")
                .lastName("User")
                .email("updated.user@example.com")
                .password("password123")
                .build();

        when(userService.update(eq(1L), any(UserDto.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Updated")))
                .andExpect(jsonPath("$.lastName", is("User")));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
}
