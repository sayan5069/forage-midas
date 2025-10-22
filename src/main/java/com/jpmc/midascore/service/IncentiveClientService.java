package com.jpmc.midascore.service;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.Incentive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IncentiveClientService {

    private static final String INCENTIVE_API_URL = "http://localhost:8080/incentive";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Calls the external Incentive API to get the incentive amount for a transaction.
     *
     * @param transaction The Transaction object (DTO) to send.
     * @return The Incentive object returned by the API.
     */
    public Incentive getIncentiveForTransaction(Transaction transaction) {
        try {
            // Spring automatically serializes the Transaction object to JSON 
            // and deserializes the response into an Incentive object.
            ResponseEntity<Incentive> response = restTemplate.postForEntity(
                INCENTIVE_API_URL,
                transaction,
                Incentive.class
            );
            return response.getBody();
        } catch (Exception e) {
            // In a real system, you'd handle failure gracefully (e.g., logging or returning zero incentive).
            System.err.println("Error calling Incentive API: " + e.getMessage());
            Incentive defaultIncentive = new Incentive();
            defaultIncentive.setAmount(0.0f);
            return defaultIncentive;
        }
    }
}