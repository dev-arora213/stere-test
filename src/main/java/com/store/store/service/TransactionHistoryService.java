package com.store.store.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.store.store.model.TransactionHistory;
import com.store.store.dto.RegisterTransactionDto;
import com.store.store.model.Order;
import com.store.store.repository.TransactionHistoryRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionHistoryService {
    private final TransactionHistoryRepository transactionRepository;
    private final OrderService transactionService;

    public TransactionHistoryService(TransactionHistoryRepository transactionRepository,
            OrderService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public TransactionHistory createTransactionHistory(
            RegisterTransactionDto registerTransactionDto) {
        Order order = transactionService.getOrderById(registerTransactionDto.getOrderId())
                .orElseThrow(() -> new DataAccessResourceFailureException("Order not found."));
        TransactionHistory transaction = new TransactionHistory(order,
                registerTransactionDto.getStatus(), registerTransactionDto.getPaymentId(), order.getTotalAmount());
        return transactionRepository.save(transaction);
    }

    public List<TransactionHistory> getAllTransactionHistorys() {
        return transactionRepository.findAll();
    }

    public Optional<TransactionHistory> getTransactionHistoryById(Long id) {
        return transactionRepository.findById(id);
    }

    public void deleteAllTransactionHistorys() {
        transactionRepository.deleteAll();
    }

    public void deleteTransactionHistory(Long id) {
        transactionRepository.deleteById(id);
    }

    // public TransactionHistory updateTransactionHistory(Long id,
    // TransactionHistory TransactionHistoryDetails) {
    // Optional<TransactionHistory> transaction =
    // transactionRepository.findById(id);
    // if (transaction.isPresent()) {
    // TransactionHistory existingTransactionHistory = transaction.get();
    // existingTransactionHistory.setTotalAmount(TransactionHistoryDetails.getTotalAmount());
    // existingTransactionHistory.setQuantity(TransactionHistoryDetails.getQuantity());
    // return transactionRepository.save(existingTransactionHistory);
    // }
    // return null;
    // }
}
