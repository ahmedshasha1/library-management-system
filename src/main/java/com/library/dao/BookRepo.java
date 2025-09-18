package com.library.dao;

import com.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepo extends JpaRepository<Book,Long> {

    boolean existsByIsbn(String isbn);
    Book getByIsbn(String isbn);
    Page<Book> getBookByPublicationYear(Integer year, Pageable pageable);
    Page<Book> getBooksByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = "SELECT b FROM Book b JOIN b.categories c WHERE c.id = :id")
    Page<Book> getByCategoryId(@Param("id")Long id, Pageable pageable);
    @Query(value = "SELECT b FROM Book b JOIN b.authors a WHERE LOWER(a.name) = LOWER(:name)")
    Page<Book> getByAuthorName(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT b FROM Book b JOIN b.categories a WHERE LOWER(a.name) = LOWER(:name)")
    Page<Book> getByCategory(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT b FROM Book b JOIN b.publisher a WHERE LOWER(a.name) = LOWER(:name)")
    Page<Book> getByPublisher(@Param("name") String name, Pageable pageable);


    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0")
    Page<Book> findAvailableBooks(Pageable pageable);


}
