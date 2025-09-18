package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends ChildBaseEntity{
    @Column(unique = true,nullable = false)
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private boolean active = true;

    @Column(name = "membership_date")
    private LocalDateTime membershipDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BorrowTransaction> borrowTransactions = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        membershipDate = LocalDateTime.now();
    }
}
