package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.dto.UserDto;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    

    
    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto testUser1;
    private UserDto testUser2;
    private List<UserDto> allUsers;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        org.mockito.MockitoAnnotations.openMocks(this);
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
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Jane")))
                .andExpect(jsonPath("$[1].lastName", is("Smith")))
                .andExpect(jsonPath("$[1].email", is("jane.smith@example.com")));
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        when(userService.findById(1L)).thenReturn(testUser1);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));
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
                .andExpect(jsonPath("$.lastName", is("User")))
                .andExpect(jsonPath("$.email", is("new.user@example.com")));
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
                .andExpect(jsonPath("$.lastName", is("User")))
                .andExpect(jsonPath("$.email", is("updated.user@example.com")));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void getUserById_WithNonExistentId_ShouldReturnNotFound() throws Exception {
        Long nonExistentId = 999L;
        when(userService.findById(nonExistentId)).thenThrow(
                new ResourceNotFoundException("User not found with id: " + nonExistentId));

        mockMvc.perform(get("/users/" + nonExistentId))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void updateUser_WithNonExistentId_ShouldReturnNotFound() throws Exception {
        Long nonExistentId = 999L;
        UserDto updatedUser = UserDto.builder()
                .id(nonExistentId)
                .firstName("Updated")
                .lastName("User")
                .email("updated.user@example.com")
                .password("password123")
                .build();

        when(userService.update(eq(nonExistentId), any(UserDto.class)))
                .thenThrow(new ResourceNotFoundException("User not found with id: " + nonExistentId));

        mockMvc.perform(put("/users/" + nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void deleteUser_WithNonExistentId_ShouldReturnNotFound() throws Exception {
        Long nonExistentId = 999L;
        
        doThrow(new ResourceNotFoundException("User not found with id: " + nonExistentId))
                .when(userService).delete(nonExistentId);

        mockMvc.perform(delete("/users/" + nonExistentId))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void createUser_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        UserDto invalidUser = UserDto.builder()
                .firstName("") // Empty first name violates @NotBlank constraint
                .lastName("User")
                .email("invalid-email") // Invalid email format
                .password("short") // Too short password
                .build();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
}
