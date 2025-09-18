package com.library.controller;

import com.library.controller.vm.CategoryResponseVM;
import com.library.dto.CategoryDto;
import com.library.dto.response.CategoryResponse;
import com.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add-category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createCategory(@RequestBody @Validated CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.created(URI.create("/addCategory/admin")).build();
    }

    @PutMapping("/update-category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Validated CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }

    @DeleteMapping("/delete/category-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/category-id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<CategoryResponseVM> getAllCategories(@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(categoryService.getAllCategory(pageNo-1,pageSize  ));
    }
    @GetMapping("/search/name/{name}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN','STAFF')")
    public ResponseEntity<CategoryResponseVM> search(@PathVariable String name,@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(categoryService.search(name, pageNo-1,pageSize  ));
    }

    @PostMapping("add/category-id/{categoryId}/book-id/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addBookToCategory(@PathVariable Long categoryId, @PathVariable Long bookId) {
        categoryService.addBookToCategory(categoryId, bookId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/category-id/{categoryId}/book-id/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeBookFromCategory(@PathVariable Long categoryId, @PathVariable Long bookId) {
        categoryService.removeBookFromCategory(categoryId, bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/parents/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<CategoryResponseVM> getParentsCategories(@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(categoryService.getParentsCategories(pageNo-1,pageSize  ));
    }

    @GetMapping("/subcategories/parent-id/{parentId}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<CategoryResponseVM> getSubCategories(@PathVariable Long parentId,@PathVariable Integer pageNo,@PathVariable Integer pageSize) {
        return ResponseEntity.ok(categoryService.getSubCategories(parentId, pageNo-1,pageSize  ));
    }
}

