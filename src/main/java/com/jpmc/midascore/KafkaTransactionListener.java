package com.jpmc.midascore;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.jpmc.midascore.foundation.Transaction; // Adjust import if needed, based on Transaction.java location

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaTransactionListener {

    // Public and static so the TaskTwoTests can access and check the received messages.
    public static final List<Transaction> receivedTransactions = new ArrayList<>();

    /**
     * Listens for transactions on the topic defined by the 'general.kafka-topic' property.
     *
     * @param transaction The incoming Transaction object, automatically mapped from JSON.
     */
    @KafkaListener(
        topics = "${general.kafka-topic}", 
        groupId = "midas-core-group",
        containerFactory = "kafkaListenerContainerFactory" // Use the factory defined in KafkaConfig
    )
    public void handleTransaction(Transaction transaction) {
        // 1. Capture the transaction for the test to verify.
        receivedTransactions.add(transaction);
        
        // 2. Print the amount to help you easily find the answer in the logs.
        System.out.println("Received Transaction Amount: " + transaction.getAmount());
    }
}