package com.library.controller;

import com.library.controller.vm.AuthorResponseVM;
import com.library.dto.AuthorDto;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/add-author")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<Void> addAuthor(@RequestBody @Validated AuthorDto authorDto) {
        authorService.addAuthor(authorDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update-author")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<AuthorDto> updateAuthor(@RequestBody @Validated AuthorDto authorDto) {
        return ResponseEntity.ok(authorService.updateAuthor(authorDto));
    }

    @DeleteMapping("/delete/author-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/author-id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @GetMapping("/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<AuthorResponseVM> getAllAuthors(@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(authorService.getAllAuthors(pageNo-1,pageSize ));
    }

    @GetMapping("/search/name/{name}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<AuthorResponseVM> getByName(@PathVariable String name,@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(authorService.getByName(name,pageNo-1 ,pageSize ));
    }
}
