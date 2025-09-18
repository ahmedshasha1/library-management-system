package com.library.dao;

import com.library.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    boolean existsByNameIgnoreCase(String name);
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Category> findByParentIsNull(Pageable pageable);
    Page<Category> findByParentId(Long parentId, Pageable pageable);

}
