package com.library.service.impl;

import com.library.controller.vm.BookResponseVM;
import com.library.dao.AuthorRepo;
import com.library.dao.BookRepo;
import com.library.dao.CategoryRepo;
import com.library.dao.PublisherRepo;
import com.library.dto.BookDto;
import com.library.dto.response.BookResponse;
import com.library.mapper.BookMapper;
import com.library.model.Author;
import com.library.model.Book;
import com.library.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private PublisherRepo publisherRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public void addNewBook(BookDto bookDto) {
        if (bookDto == null) throw new RuntimeException("invalid.data");
        if (Objects.nonNull(bookDto.getId())) throw new RuntimeException("id.nonnull");
        if (bookRepo.existsByIsbn(bookDto.getIsbn())) throw new RuntimeException("isbn.exist");

        Book book = BookMapper.mapper.toEntity(bookDto);
        book.setPublisher(publisherRepo.findById(bookDto.getPublisherId())
                .orElseThrow(() -> new RuntimeException("publisher.not.found")));
        book.setAuthors(new HashSet<>(authorRepo.findAllById(bookDto.getAuthorIds())));
        book.setCategories(new HashSet<>(categoryRepo.findAllById(bookDto.getCategoryIds())));

        bookRepo.save(book);
    }

    @Override
    public BookResponse updateBook(BookDto bookDto) {
        if (bookDto == null) throw new RuntimeException("invalid.data");
        if (Objects.isNull(bookDto.getId())) throw new RuntimeException("id.empty");

        Book book = bookRepo.findById(bookDto.getId())
                .orElseThrow(() -> new RuntimeException("book.not.found"));

        if (!book.getIsbn().equals(bookDto.getIsbn()))
            throw new RuntimeException("isbn.cannot.change");

        BookMapper.mapper.updateBookFromDto(bookDto, book);
        book.setPublisher(publisherRepo.findById(bookDto.getPublisherId())
                .orElseThrow(() -> new RuntimeException("publisher.not.found")));
        book.setAuthors(new HashSet<>(authorRepo.findAllById(bookDto.getAuthorIds())));
        book.setCategories(new HashSet<>(categoryRepo.findAllById(bookDto.getCategoryIds())));

        return BookMapper.mapper.toResponseDto(bookRepo.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("book.not.found"));
        bookRepo.delete(book);

    }

    @Override
    public BookResponse getById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("book.not.found"));
        return BookMapper.mapper.toResponseDto(book);
    }

    @Override
    public BookResponseVM getAllBooks(Integer pageNo, Integer pageSize) {
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Book> books = bookRepo.findAll(pageable);
        return new BookResponseVM(
                BookMapper.mapper.toResponseDtoList(books.getContent()),
                books.getTotalElements()
        );
    }

    @Override
    public BookResponseVM searchByName(String name, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Book> books = bookRepo.getBooksByNameContainingIgnoreCase(name,pageable);
        return new BookResponseVM(
                BookMapper.mapper.toResponseDtoList(books.getContent()),
                books.getTotalElements()
        );
    }

    @Override
    public void addAuthorToBook(Long bookId, Long authorId) {
        if (Objects.isNull(bookId) ||Objects.isNull(authorId) ) {
            throw new RuntimeException("id.empty");
        }
        Author author = authorRepo.findById(authorId)
                .orElseThrow(()->new RuntimeException("author.not.found"));
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book.not.found"));

        if (book.getAuthors().contains(author)) {
            throw new RuntimeException("author.exist");
        }
        book.getAuthors().add(author);
        author.getBooks().add(book);
        bookRepo.save(book);
    }
    @Override
    public BookResponse removeAuthorFromBook(Long bookId, Long authorId) {
        if (Objects.isNull(bookId) ||Objects.isNull(authorId) ) {
            throw new RuntimeException("id.empty");
        }
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book.not.found"));

        Author author = authorRepo.findById(authorId)
                .orElseThrow(() -> new RuntimeException("author.not.found"));

        if (!book.getAuthors().contains(author)) {
            throw new RuntimeException("author.not.exist");
        }
        if (book.getAuthors().size() == 1) {
            throw new RuntimeException("invalid.author");
        }

        book.getAuthors().remove(author);
        author.getBooks().remove(book);

        Book updatedBook = bookRepo.save(book);
        return BookMapper.mapper.toResponseDto(updatedBook);
    }

    @Override
    public BookResponseVM getByAuthor(String name, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Book> books = bookRepo.getByAuthorName(name,pageable);
        return new BookResponseVM(
                BookMapper.mapper.toResponseDtoList(books.getContent()),
                books.getTotalElements()
        );
    }

    @Override
    public BookResponseVM getByCategoryName(String name, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Book> books = bookRepo.getByCategory(name,pageable);
        return new BookResponseVM(
                BookMapper.mapper.toResponseDtoList(books.getContent()),
                books.getTotalElements()
        );
    }

    @Override
    public BookResponseVM getByCategoryId(Long id, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }

        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Book> books = bookRepo.getByCategoryId(id,pageable);
        return new BookResponseVM(
                BookMapper.mapper.toResponseDtoList(books.getContent()),
                books.getTotalElements()
        );    }

    @Override
    public BookResponseVM getByPublisher(String name, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Book> books = bookRepo.getByPublisher(name,pageable);
        return new BookResponseVM(
                BookMapper.mapper.toResponseDtoList(books.getContent()),
                books.getTotalElements()
        );
    }

    @Override
    public BookResponse getByISBN(String isbn) {
        if (Objects.isNull(isbn)) {
            throw new RuntimeException("invalid.data");
        }
        if(!bookRepo.existsByIsbn(isbn)){
            throw new RuntimeException("book.not.found");
        }
        return BookMapper.mapper.toResponseDto(bookRepo.getByIsbn(isbn));
    }

    @Override
    public BookResponseVM getByPublicationYear(Integer publicationYear, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(publicationYear)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Book> books = bookRepo.getBookByPublicationYear(publicationYear,pageable);
        return new BookResponseVM(
                BookMapper.mapper.toResponseDtoList(books.getContent()),
                books.getTotalElements()
        );
    }

    @Override
    public BookResponseVM getAvailableBooks(Integer pageNo, Integer pageSize) {
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Book> books = bookRepo.findAvailableBooks(pageable);
        return new BookResponseVM(
                BookMapper.mapper.toResponseDtoList(books.getContent()),
                books.getTotalElements()
        );    }

    public boolean isBookAvailable(Long bookId) {
        if (Objects.isNull(bookId)) {
            throw new RuntimeException("id.empty");
        }
        BookResponse response = getById(bookId);

        return response.getAvailableCopies() > 0;
    }

    public void updateBookAvailability(Long bookId, Integer change) {
        if (Objects.isNull(bookId)||Objects.isNull(change)) {
            throw new RuntimeException("invalid.data");
        }

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book.not.found"));
        Integer newAvailable = book.getAvailableCopies() + change;
        if (newAvailable < 0 || newAvailable > book.getTotalCopies()) {
            throw new RuntimeException("invalid.copy.count");
        }
        book.setAvailableCopies(newAvailable);
        bookRepo.save(book);

    }
}
