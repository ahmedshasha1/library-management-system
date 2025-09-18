package com.library.controller;

import com.library.controller.vm.BorrowTransactionResponseVM;
import com.library.dto.response.BorrowTransactionResponse;
import com.library.enumration.Transaction;
import com.library.service.BorrowTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private BorrowTransactionService borrowTransactionService;

    @GetMapping("/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BorrowTransactionResponseVM> getAllTransactions(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(borrowTransactionService.getAllTransactions(pageNo-1,pageSize ));
    }

    @GetMapping("/transaction-id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BorrowTransactionResponse> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowTransactionService.getTransactionById(id));
    }

    @PostMapping("/borrow/book-id/{bookId}/member-id/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<Void> borrowBook(@PathVariable Long bookId, @PathVariable Long memberId) {
        borrowTransactionService.borrowBook(bookId, memberId);
        return ResponseEntity.created(URI.create("/api/transactions/borrow")).build();
    }

    @PutMapping("/return/transaction-id/{transactionId}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<Void> returnBook(@PathVariable Long transactionId) {
        borrowTransactionService.returnBook(transactionId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/member/{memberId}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BorrowTransactionResponseVM> getMemberTransactions(@PathVariable Long memberId,@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(borrowTransactionService.getMemberTransactions(memberId, pageNo-1,pageSize ));
    }

    @GetMapping("/member/{memberId}/status/{status}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BorrowTransactionResponseVM> getMemberTransactionsByStatus(
            @PathVariable Long memberId,
            @PathVariable Transaction status,
            @PathVariable Integer pageNo,
            @PathVariable Integer pageSize) {
        return ResponseEntity.ok(borrowTransactionService.getMemberTransactionsByStatus(memberId, status, pageNo-1,pageSize ));
    }

    @GetMapping("/book/{bookId}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BorrowTransactionResponseVM> getTransactionByBook(@PathVariable Long bookId,@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(borrowTransactionService.getTransactionByBook(bookId, pageNo-1,pageSize ));
    }

    @GetMapping("/status/{status}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BorrowTransactionResponseVM> getTransactionByStatus(@PathVariable Transaction status,@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(borrowTransactionService.getTransactionByStatus(status, pageNo-1,pageSize ));
    }
}
