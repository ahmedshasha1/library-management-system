package com.library.dao;

import com.library.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends JpaRepository<Author,Long> {

    Page<Author> getByNameContainingIgnoreCase(String name, Pageable pageable);
}
