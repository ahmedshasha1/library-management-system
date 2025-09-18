package com.library.controller;

import com.library.controller.vm.BookResponseVM;
import com.library.dto.BookDto;
import com.library.dto.response.BookResponse;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/add-book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addNewBook(@RequestBody @Validated BookDto bookDto) {
        bookService.addNewBook(bookDto);
        return ResponseEntity.created(URI.create("/api/books/add-book")).build();
    }

    @PutMapping("/update-book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> updateBook(@RequestBody @Validated BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(bookDto));
    }

    @DeleteMapping("/delete-book/book-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/book-id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @GetMapping("/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BookResponseVM> getAllBooks(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(bookService.getAllBooks(pageNo-1,pageSize));
    }

    // Search
    @GetMapping("/search/name/{name}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BookResponseVM> searchByName(@PathVariable String name,@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(bookService.searchByName(name, pageNo-1,pageSize ));
    }

    @GetMapping("/author/name/{name}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BookResponseVM> getByAuthor(@PathVariable String name,@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(bookService.getByAuthor(name, pageNo-1,pageSize ));
    }

    @GetMapping("/category/name/{name}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BookResponseVM> getByCategoryName(@PathVariable String name,@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(bookService.getByCategoryName(name, pageNo-1,pageSize ));
    }

    @GetMapping("/category/{id}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BookResponseVM> getByCategoryId(@PathVariable Long id,@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(bookService.getByCategoryId(id, pageNo-1,pageSize ));
    }

    @GetMapping("/publisher/name/{name}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BookResponseVM> getByPublisher(@PathVariable String name,@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(bookService.getByPublisher(name, pageNo-1,pageSize ));
    }

    @GetMapping("/isbn/{isbn}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BookResponse> getByISBN(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getByISBN(isbn));
    }

    @GetMapping("/year/{publicationYear}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<BookResponseVM> getByPublicationYear(@PathVariable Integer publicationYear,@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(bookService.getByPublicationYear(publicationYear, pageNo-1,pageSize ));
    }

    // Author
    @PostMapping("/book-id/{bookId}/author-id/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookService.addAuthorToBook(bookId, authorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/book-id/{bookId}/author-id/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> removeAuthorFromBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        return ResponseEntity.ok(bookService.removeAuthorFromBook(bookId, authorId));
    }

    // Availability
    @GetMapping("/available/page-number/{pageNo}/page-size/{pageSize}")
    public ResponseEntity<BookResponseVM> getAvailableBooks(@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(bookService.getAvailableBooks(pageNo-1,pageSize ));
    }

    @GetMapping("/book-id/{bookId}/is-available")
    public ResponseEntity<Boolean> isBookAvailable(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.isBookAvailable(bookId));
    }

    @PutMapping("/book-id/{bookId}/availability")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateBookAvailability(@PathVariable Long bookId,
                                                       @RequestParam Integer change) {
        bookService.updateBookAvailability(bookId, change);
        return ResponseEntity.ok().build();
    }
}

