package com.library.service.impl;

import com.library.controller.vm.BorrowTransactionResponseVM;
import com.library.dao.BookRepo;
import com.library.dao.BorrowTransactionRepo;
import com.library.dao.MemberRepo;
import com.library.dto.response.BorrowTransactionResponse;
import com.library.enumration.Transaction;
import com.library.mapper.BorrowTransactionMapper;
import com.library.model.Book;
import com.library.model.BorrowTransaction;
import com.library.model.Member;
import com.library.service.BookService;
import com.library.service.BorrowTransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BorrowTransactionServiceImpl implements BorrowTransactionService {
    @Autowired
    private BorrowTransactionRepo borrowTransactionRepo;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private MemberRepo memberRepo;

    @Override
    public BorrowTransactionResponseVM getAllTransactions(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<BorrowTransaction> transactions = borrowTransactionRepo.findAll(pageable);
        return new BorrowTransactionResponseVM(
                BorrowTransactionMapper.mapper.toResponseList(transactions.getContent()),
                transactions.getTotalElements()
        );
    }

    @Override
    public BorrowTransactionResponse getTransactionById(Long id) {
        if(Objects.isNull(id)){
            throw new RuntimeException("id.empty");
        }
        BorrowTransaction borrowTransaction = borrowTransactionRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("transaction.not.found"));
        return BorrowTransactionMapper.mapper.toResponseDto(borrowTransaction);
    }

    @Override
    public void borrowBook(Long bookId, Long memberId) {
        if (Objects.isNull(bookId) ||Objects.isNull(memberId) ) {
            throw new RuntimeException("id.empty");
        }
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book.not.found"));
        Member member =memberRepo.findById(memberId)
                .orElseThrow(()->new RuntimeException("member.not.found"));
        member.setId(memberId);
        if(!bookService.isBookAvailable(bookId)){
            throw new RuntimeException("book.not.available");
        }
        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setMember(member);
        transaction.setBook(book);
        transaction.setBorrowDate(LocalDateTime.now());
        transaction.setStatus(Transaction.BORROWED);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);
    }

    @Override
    public void returnBook(Long transactionId) {
        if(Objects.isNull(transactionId)){
            throw new RuntimeException("id.empty");
        }
        BorrowTransaction borrowTransaction = borrowTransactionRepo.findById(transactionId)
                .orElseThrow(()->new RuntimeException("transaction.not.found"));
        if(borrowTransaction.getStatus() != Transaction.BORROWED){
            throw new RuntimeException("book.borrow");
        }
        borrowTransaction.setStatus(Transaction.RETURNED);
        BorrowTransaction saved =borrowTransactionRepo.save(borrowTransaction);
        bookService.updateBookAvailability(borrowTransaction.getBook().getId(),1);
    }

    @Override
    public BorrowTransactionResponseVM getMemberTransactions(Long memberId, Integer pageNo, Integer pageSize) {
        if(Objects.isNull(memberId)){
            throw new RuntimeException("id.empty");
        }
        Member member = memberRepo.findById(memberId)
                .orElseThrow(()->new RuntimeException("member.not.found"));

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<BorrowTransaction> transactions = borrowTransactionRepo.findByMemberId(memberId,pageable);
        return new BorrowTransactionResponseVM(
                BorrowTransactionMapper.mapper.toResponseList(transactions.getContent()),
                transactions.getTotalElements()
        );
    }

    @Override
    public BorrowTransactionResponseVM getTransactionByBook(Long bookId, Integer pageNo, Integer pageSize) {
        if(Objects.isNull(bookId)){
            throw new RuntimeException("id.empty");
        }
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book.not.found"));
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<BorrowTransaction> transactions = borrowTransactionRepo.findByBookId(bookId,pageable);
        return new BorrowTransactionResponseVM(
                BorrowTransactionMapper.mapper.toResponseList(transactions.getContent()),
                transactions.getTotalElements()
        );
    }

    @Override
    public BorrowTransactionResponseVM getTransactionByStatus(Transaction status, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(status)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<BorrowTransaction> transactions = borrowTransactionRepo.findByStatus(status,pageable);
        return new BorrowTransactionResponseVM(
                BorrowTransactionMapper.mapper.toResponseList(transactions.getContent()),
                transactions.getTotalElements()
        );
    }

    @Override
    public BorrowTransactionResponseVM getMemberTransactionsByStatus(Long memberId, Transaction status, Integer pageNo, Integer pageSize) {
        if ((Objects.isNull(memberId)||Objects.isNull(status))) {
            throw new RuntimeException("invalid.data");
        }
        Member member = memberRepo.findById(memberId)
                .orElseThrow(()-> new RuntimeException("member.not.found"));
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<BorrowTransaction> transactions = borrowTransactionRepo.findByMember_IdAndStatus(memberId,status,pageable);
        return new BorrowTransactionResponseVM(
                BorrowTransactionMapper.mapper.toResponseList(transactions.getContent()),
                transactions.getTotalElements()
        );
    }

}
