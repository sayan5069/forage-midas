package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.User; // Import the correct User entity
import org.springframework.data.jpa.repository.JpaRepository; // Use JpaRepository for richer features
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 1. The primary key type is now String (the type of User's 'userId' field).
    // 2. We use Optional<User> for safety when looking up by ID.
    Optional<User> findByUserId(String userId);

    // Note: If the test harness relies on the old findById(long id), 
    // you may need to add a temporary custom query here, but 
    // for the main business logic, use findByUserId(String).
}
