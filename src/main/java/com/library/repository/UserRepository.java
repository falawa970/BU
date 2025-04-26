package com.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.model.User;
import com.library.model.User.UserType;

/**
 * Repository for user operations
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find a user by email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find users by their type (student, professor, librarian)
     */
    List<User> findByType(UserType type);
    
    /**
     * Find users by their last name (case insensitive)
     */
    List<User> findByNomContainingIgnoreCase(String nom);
}
