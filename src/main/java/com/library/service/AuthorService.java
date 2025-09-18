package com.library.service;

import com.library.controller.vm.AuthorResponseVM;
import com.library.dto.AuthorDto;

public interface AuthorService {

    void addAuthor(AuthorDto authorDto);
    AuthorDto updateAuthor(AuthorDto authorDto);
    void deleteAuthor(Long id);
    AuthorDto getById(Long id);
    AuthorResponseVM getAllAuthors(Integer pageNo, Integer pageSize);
    AuthorResponseVM getByName(String name,Integer pageNo,Integer pageSize);


}
