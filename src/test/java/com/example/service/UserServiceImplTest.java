package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.dto.UserDto;
import com.example.model.entity.User;
import com.example.repository.UserRepository;
import com.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;
    private UserDto userDto1;
    private UserDto userDto2;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("password456")
                .build();

        userDto1 = UserDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        userDto2 = UserDto.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .build();
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        // Given
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        // When
        List<UserDto> result = userService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(userRepository).findAll();
        verify(userMapper, times(2)).toDto(any(User.class));
    }

    @Test
    void findById_WithExistingId_ShouldReturnUser() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userMapper.toDto(user1)).thenReturn(userDto1);

        // When
        UserDto result = userService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        verify(userRepository).findById(1L);
        verify(userMapper).toDto(user1);
    }

    @Test
    void findById_WithNonExistingId_ShouldThrowException() {
        // Given
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(99L));
        verify(userRepository).findById(99L);
        verify(userMapper, never()).toDto(any(User.class));
    }

    @Test
    void save_ShouldReturnSavedUser() {
        // Given
        UserDto newUserDto = UserDto.builder()
                .firstName("New")
                .lastName("User")
                .email("new.user@example.com")
                .password("password789")
                .build();

        User newUser = User.builder()
                .firstName("New")
                .lastName("User")
                .email("new.user@example.com")
                .password("password789")
                .build();

        User savedUser = User.builder()
                .id(3L)
                .firstName("New")
                .lastName("User")
                .email("new.user@example.com")
                .password("password789")
                .build();

        UserDto savedUserDto = UserDto.builder()
                .id(3L)
                .firstName("New")
                .lastName("User")
                .email("new.user@example.com")
                .build();

        when(userMapper.toEntity(newUserDto)).thenReturn(newUser);
        when(userRepository.save(newUser)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(savedUserDto);

        // When
        UserDto result = userService.save(newUserDto);

        // Then
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("New", result.getFirstName());
        verify(userMapper).toEntity(newUserDto);
        verify(userRepository).save(newUser);
        verify(userMapper).toDto(savedUser);
    }

    @Test
    void update_WithExistingId_ShouldReturnUpdatedUser() {
        // Given
        UserDto updateUserDto = UserDto.builder()
                .firstName("Updated")
                .lastName("User")
                .email("updated.user@example.com")
                .password("newpassword")
                .build();

        User updatedUser = User.builder()
                .id(1L)
                .firstName("Updated")
                .lastName("User")
                .email("updated.user@example.com")
                .password("newpassword")
                .build();

        UserDto updatedUserDto = UserDto.builder()
                .id(1L)
                .firstName("Updated")
                .lastName("User")
                .email("updated.user@example.com")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userMapper.toEntity(updateUserDto)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(updatedUserDto);

        // When
        UserDto result = userService.update(1L, updateUserDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated", result.getFirstName());
        verify(userRepository).findById(1L);
        verify(userMapper).toEntity(updateUserDto);
        verify(userRepository).save(updatedUser);
        verify(userMapper).toDto(updatedUser);
    }

    @Test
    void update_WithNonExistingId_ShouldThrowException() {
        // Given
        UserDto updateUserDto = UserDto.builder()
                .firstName("Updated")
                .lastName("User")
                .email("updated.user@example.com")
                .password("newpassword")
                .build();

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userService.update(99L, updateUserDto));
        verify(userRepository).findById(99L);
        verify(userMapper, never()).toEntity(any(UserDto.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void delete_WithExistingId_ShouldDeleteUser() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        // When
        userService.delete(1L);

        // Then
        verify(userRepository).findById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void delete_WithNonExistingId_ShouldThrowException() {
        // Given
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userService.delete(99L));
        verify(userRepository).findById(99L);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
