package com.library.service;


import com.library.controller.vm.CategoryResponseVM;
import com.library.dto.CategoryDto;
import com.library.dto.response.CategoryResponse;

public interface CategoryService {

    void createCategory (CategoryDto categoryDto);
    CategoryResponse updateCategory(CategoryDto categoryDto );
    void deleteCategory (Long id);
    CategoryResponse getCategoryById (Long id);
    CategoryResponseVM search(String name,Integer pageNo, Integer pageSize);

    CategoryResponseVM getAllCategory (Integer pageNo, Integer pageSize);
    void addBookToCategory(Long categoryId, Long bookId);
    void removeBookFromCategory(Long categoryId, Long bookId);
    CategoryResponseVM getParentsCategories(Integer pageNo, Integer pageSize);
    CategoryResponseVM getSubCategories(Long parentId,Integer pageNo, Integer pageSize);
}
