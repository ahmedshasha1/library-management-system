package com.library.dao;

import com.library.enumration.Transaction;
import com.library.model.BorrowTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BorrowTransactionRepo extends JpaRepository<BorrowTransaction,Long> {
    Page<BorrowTransaction> findByMemberId(Long memberId, Pageable pageable);
    Page<BorrowTransaction> findByBookId(Long bookId, Pageable pageable);
    Page<BorrowTransaction> findByStatus(Transaction status, Pageable pageable);
    Page<BorrowTransaction> findByMember_IdAndStatus(Long memberId, Transaction status, Pageable pageable);
}
