package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.User;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jpmc.midascore.foundation.Incentive;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;
    
    @Autowired // <-- NEW: Inject the Incentive Client
    private IncentiveClientService incentiveClientService; 

    @Transactional
    public void processTransaction(Transaction transaction) {
        String senderId = transaction.getSenderId();
        String recipientId = transaction.getRecipientId();
        // Use BigDecimal for accurate calculation
        BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());

        User sender = userRepository.findByUserId(senderId).orElse(null);
        User recipient = userRepository.findByUserId(recipientId).orElse(null);

        if (sender == null || recipient == null) {
            return; // Discard: Invalid senderId or recipientId
        }

        // Validate sufficient balance (sender balance >= amount)
        if (sender.getBalance().compareTo(amount) < 0) {
            return; // Discard: Insufficient balance
        }

        // --- VALIDATION SUCCESSFUL: Process Incentive, Record, and Adjust Balances ---

        // 1. Get Incentive amount
        Incentive incentive = incentiveClientService.getIncentiveForTransaction(transaction);
        BigDecimal incentiveAmount = BigDecimal.valueOf(incentive.getAmount());

        // 2. Adjust balances:
        // Deduction from sender: ONLY transaction amount (incentive is NOT deducted)
        sender.setBalance(sender.getBalance().subtract(amount));
        
        // Addition to recipient: transaction amount + incentive amount
        BigDecimal totalAddition = amount.add(incentiveAmount);
        recipient.setBalance(recipient.getBalance().add(totalAddition));

        // 3. Save updated users
        userRepository.save(sender);
        userRepository.save(recipient);

        // 4. Record the transaction (including incentive)
        TransactionRecord record = new TransactionRecord();
        record.setAmount(amount);
        record.setIncentiveAmount(incentiveAmount); // <-- NEW: Record incentive amount
        record.setSender(sender);
        record.setRecipientId(recipientId);
        
        transactionRecordRepository.save(record);
    }
}