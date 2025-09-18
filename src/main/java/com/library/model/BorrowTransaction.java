package com.library.model;

import com.library.enumration.Transaction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrow_transactions")
public class BorrowTransaction extends BaseEntity {
    @Column(name = "borrow_date", nullable = false)
    private LocalDateTime borrowDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @Enumerated(EnumType.STRING)
    private Transaction status = Transaction.BORROWED;

    @PrePersist
    protected void onCreate() {
        borrowDate = LocalDateTime.now();
    }
}
