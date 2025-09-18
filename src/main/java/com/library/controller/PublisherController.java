package com.library.controller;

import com.library.controller.vm.PublisherResponseVM;
import com.library.dto.PublisherDto;
import com.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/add-publisher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addPublisher(@RequestBody @Validated PublisherDto publisherDto) {
        publisherService.addPublisher(publisherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Publisher created successfully");
    }

    @PutMapping("/update-publisher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PublisherDto> updatePublisher(@RequestBody @Validated PublisherDto publisherDto) {
        return ResponseEntity.ok(publisherService.updatePublisher(publisherDto));
    }

    @DeleteMapping("/delete/publisher-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/publisher-id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<PublisherDto> getPublisherById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.getById(id));
    }

    @GetMapping("/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<PublisherResponseVM> getAllPublishers( @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(publisherService.getAllPublishers(pageNo-1,pageSize ));
    }

    @GetMapping("/search/name/{name}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<PublisherResponseVM> getByName(@PathVariable String name, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(publisherService.getByName(name, pageNo-1,pageSize ));
    }
}
