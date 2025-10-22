package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.User;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController // Marks this class as a REST Controller
public class BalanceController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Exposes the endpoint GET http://localhost:33400/balance?userId=...
     */
    @GetMapping("/balance")
    public Balance getBalance(@RequestParam String userId) {
        
        // 1. Find the user in the database
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            // 2. User exists: return the balance from the User entity
            User user = userOptional.get();
            
            // Note: floatValue() converts the BigDecimal to a float for the Balance DTO.
            return new Balance(user.getBalance().floatValue());
        } else {
            // 3. User does not exist: return a Balance of 0.0f
            return new Balance(0.0f);
        }
    }
}