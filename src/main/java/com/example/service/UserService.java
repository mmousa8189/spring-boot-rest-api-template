package com.example.service;

import com.example.model.dto.UserDto;

import java.util.List;

/**
 * Service Interface for managing User entities.
 */
public interface UserService {

    /**
     * Get all users.
     *
     * @return the list of users
     */
    List<UserDto> findAll();

    /**
     * Get user by id.
     *
     * @param id the id of the user
     * @return the user
     */
    UserDto findById(Long id);

    /**
     * Save a user.
     *
     * @param userDto the user to save
     * @return the saved user
     */
    UserDto save(UserDto userDto);

    /**
     * Update a user.
     *
     * @param id the id of the user to update
     * @param userDto the user to update
     * @return the updated user
     */
    UserDto update(Long id, UserDto userDto);

    /**
     * Delete a user by id.
     *
     * @param id the id of the user to delete
     */
    void delete(Long id);
}
