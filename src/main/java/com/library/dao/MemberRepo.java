package com.library.dao;

import com.library.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    Page<Member> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Member> findByActive(boolean active,Pageable pageable);
}
