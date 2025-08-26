package com.example.repository;

import com.example.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find a user by email.
     *
     * @param email the email of the user
     * @return the user
     */
    User findByEmail(String email);
    
    /**
     * Check if a user with the given email exists.
     *
     * @param email the email to check
     * @return true if a user with the email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
