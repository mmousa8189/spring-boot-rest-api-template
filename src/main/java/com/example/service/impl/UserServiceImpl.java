package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.dto.UserDto;
import com.example.model.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing User entities.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Get all users.
     *
     * @return the list of users
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get user by id.
     *
     * @param id the id of the user
     * @return the user
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    /**
     * Save a user.
     *
     * @param userDto the user to save
     * @return the saved user
     */
    @Override
    public UserDto save(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Update a user.
     *
     * @param id the id of the user to update
     * @param userDto the user to update
     * @return the updated user
     */
    @Override
    public UserDto update(Long id, UserDto userDto) {
        // Verify user exists
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Set the ID to ensure we're updating the correct entity
        userDto.setId(id);
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Delete a user by id.
     *
     * @param id the id of the user to delete
     */
    @Override
    public void delete(Long id) {
        // Verify user exists
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        userRepository.deleteById(id);
    }
}
