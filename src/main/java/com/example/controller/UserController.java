package com.example.controller;

import com.example.annotation.AuditableApi;
import com.example.model.dto.UserDto;
import com.example.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user operations.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * GET /users : Get all users
     *
     * @return the ResponseEntity with status 200 (OK) and the list of users
     */
    @GetMapping
    @AuditableApi(action = "get_all_users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * GET /users/{id} : Get user by id
     *
     * @param id the id of the user to retrieve
     * @return the ResponseEntity with status 200 (OK) and the user, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @AuditableApi(action = "get_user_by_id")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * POST /users : Create a new user
     *
     * @param userDto the user to create
     * @return the ResponseEntity with status 201 (Created) and the new user
     */
    @PostMapping
    @AuditableApi(action = "create_user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {
        
        UserDto createdUser = userService.save(userDto);    
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * PUT /users/{id} : Update an existing user
     *
     * @param id the id of the user to update
     * @param userDto the user to update
     * @return the ResponseEntity with status 200 (OK) and the updated user
     */
    @PutMapping("/{id}")
    @AuditableApi(action = "update_user")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.update(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * DELETE /users/{id} : Delete a user
     *
     * @param id the id of the user to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    @AuditableApi(action = "delete_user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
