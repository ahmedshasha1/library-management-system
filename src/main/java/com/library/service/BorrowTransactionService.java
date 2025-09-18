package com.library.service;

import com.library.controller.vm.BorrowTransactionResponseVM;
import com.library.dto.response.BorrowTransactionResponse;
import com.library.enumration.Transaction;

public interface BorrowTransactionService {

    BorrowTransactionResponseVM getAllTransactions(Integer pageNo, Integer pageSize);
    BorrowTransactionResponse getTransactionById(Long id);
    void borrowBook(Long bookId, Long memberId);
    void returnBook(Long transactionId);
    BorrowTransactionResponseVM getMemberTransactions(Long memberId,Integer pageNo, Integer pageSize);

    BorrowTransactionResponseVM getTransactionByBook(Long bookId,Integer pageNo, Integer pageSize);
    BorrowTransactionResponseVM getTransactionByStatus(Transaction status,Integer pageNo, Integer pageSize);
    BorrowTransactionResponseVM getMemberTransactionsByStatus(Long memberId, Transaction status,Integer pageNo, Integer pageSize);



}
