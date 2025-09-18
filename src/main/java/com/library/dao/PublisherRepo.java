package com.library.dao;

import com.library.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepo extends JpaRepository<Publisher,Long> {

    Page<Publisher> getByNameContainingIgnoreCase(String name, Pageable pageable);
}
