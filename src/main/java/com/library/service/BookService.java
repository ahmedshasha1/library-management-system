package com.library.service;

import com.library.controller.vm.BookResponseVM;
import com.library.dto.BookDto;
import com.library.dto.response.BookResponse;

public interface BookService {
    void addNewBook(BookDto bookDto);
    BookResponse updateBook(BookDto bookDto);
    void deleteBook(Long id);
    BookResponse getById(Long id);
    BookResponseVM getAllBooks(Integer pageNo, Integer pageSize);
    BookResponseVM searchByName(String name,Integer pageNo, Integer pageSize);
    void addAuthorToBook(Long bookId, Long authorId);
    BookResponse removeAuthorFromBook(Long bookId, Long authorId);
    BookResponseVM getByAuthor(String name,Integer pageNo, Integer pageSize);
    BookResponseVM getByCategoryName(String name,Integer pageNo, Integer pageSize);
    BookResponseVM getByCategoryId(Long id,Integer pageNo, Integer pageSize);
    BookResponseVM getByPublisher(String name,Integer pageNo, Integer pageSize);
    BookResponse getByISBN(String isbn);
    BookResponseVM getByPublicationYear(Integer publicationYear,Integer pageNo, Integer pageSize);

    BookResponseVM getAvailableBooks(Integer pageNo, Integer pageSize);
    boolean isBookAvailable(Long bookId);
    void updateBookAvailability(Long bookId, Integer change);



}
