package com.library.service.impl;

import com.library.controller.vm.CategoryResponseVM;
import com.library.dao.BookRepo;
import com.library.dao.CategoryRepo;
import com.library.dto.CategoryDto;
import com.library.dto.response.CategoryResponse;
import com.library.mapper.CategoryMapper;
import com.library.model.Book;
import com.library.model.Category;
import com.library.service.BookService;
import com.library.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private BookService bookService;


    @Override
    public void createCategory(CategoryDto categoryDto) {
        if (categoryDto == null) {
            throw new RuntimeException("invalid.data");
        }

        if (categoryRepo.existsByNameIgnoreCase(categoryDto.getName())) {
            throw new RuntimeException("category.exist");
        }

        Category category = CategoryMapper.mapper.toEntity(categoryDto);

        if (categoryDto.getParentId() != null) {
            Category parent = categoryRepo.findById(categoryDto.getParentId())
                    .orElseThrow(() -> new RuntimeException("parent.not.found"));

            category.setParent(parent);
            parent.getSubcategories().add(category);
        } else {
            category.setParent(null);
        }

        categoryRepo.save(category);
    }

    @Override
    public CategoryResponse updateCategory(CategoryDto categoryDto) {
        if (categoryDto == null) {
            throw new RuntimeException("invalid.data");
        }
        if (Objects.isNull(categoryDto.getId())) {
            throw new RuntimeException("id.empty");
        }
        Category category =  categoryRepo.findById(categoryDto.getId())
                .orElseThrow(()-> new RuntimeException("category.not.found"));

        if(categoryRepo.existsByNameIgnoreCase(categoryDto.getName())){
            throw new RuntimeException("category.exist");
        }
        if (categoryDto.getParentId() != null) {
            Category parent = categoryRepo.findById(categoryDto.getParentId())
                    .orElseThrow(() -> new RuntimeException("parent.not.found"));
            category.setParent(parent);
        }
        CategoryMapper.mapper.updateCategoryFromDto(categoryDto,category);
        Category updateCategory=categoryRepo.save(category);

        return CategoryMapper.mapper.toResponseDto(updateCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        Category category =  categoryRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("category.not.found"));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        Category category =  categoryRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("category.not.found"));
        return CategoryMapper.mapper.toResponseDto(category);
    }

    @Override
    public CategoryResponseVM search(String name, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Category> categories=categoryRepo.findByNameContainingIgnoreCase(name,pageable);
        return new CategoryResponseVM(
                CategoryMapper.mapper.toResponseDtoList(categories.getContent()),
                categories.getTotalElements()
        );
    }

    @Override
    public CategoryResponseVM getAllCategory(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Category> categories=categoryRepo.findAll(pageable);
        return new CategoryResponseVM(
                CategoryMapper.mapper.toResponseDtoList(categories.getContent()),
                categories.getTotalElements()
        );
    }

    public void addBookToCategory(Long categoryId, Long bookId) {
        if (Objects.isNull(bookId) ||Objects.isNull(categoryId) ) {
            throw new RuntimeException("id.empty");
        }
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("category.not.found"));
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book.not.found"));
        if (book.getCategories().contains(category)) {
            throw new RuntimeException("category.exist");
        }
        category.getBooks().add(book);
        book.getCategories().add(category);

        bookRepo.save(book);
    }

    public void removeBookFromCategory(Long categoryId, Long bookId) {
        if (Objects.isNull(bookId) ||Objects.isNull(categoryId) ) {
            throw new RuntimeException("id.empty");
        }
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("category.not.found"));
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book.not.found"));

        if (!book.getCategories().contains(category)) {
            throw new RuntimeException("category.not.exist");
        }
        category.getBooks().remove(book);
        book.getCategories().remove(category);


        bookRepo.save(book);
    }

    public CategoryResponseVM getParentsCategories(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Category> categories=categoryRepo.findByParentIsNull(pageable);
        return new CategoryResponseVM(
                CategoryMapper.mapper.toResponseDtoList(categories.getContent()),
                categories.getTotalElements()
        );

    }
    public CategoryResponseVM getSubCategories(Long parentId, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(parentId)) {
            throw new RuntimeException("id.empty");
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Category> categories=categoryRepo.findByParentId(parentId,pageable);
        return new CategoryResponseVM(
                CategoryMapper.mapper.toResponseDtoList(categories.getContent()),
                categories.getTotalElements()
        );
    }

}
