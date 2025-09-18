package com.library.service.impl;

import com.library.controller.vm.AuthorResponseVM;
import com.library.dao.AuthorRepo;
import com.library.dto.AuthorDto;
import com.library.mapper.AuthorMapper;
import com.library.model.Author;
import com.library.service.AuthorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public void addAuthor(AuthorDto authorDto) {
        if(authorDto == null){
            throw new RuntimeException("invalid.data");
        }
        if(Objects.nonNull(authorDto.getId())){
            throw new RuntimeException("id.nonnull");
        }
        authorRepo.save(AuthorMapper.mapper.toEntity(authorDto));
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        if (authorDto == null) {
            throw new RuntimeException("invalid.data");
        }
        if (Objects.isNull(authorDto.getId())) {
            throw new RuntimeException("id.empty");
        }
        Author author = authorRepo.findById(authorDto.getId())
                .orElseThrow(()->new RuntimeException("author.not.found"));

        AuthorMapper.mapper.updateFromDto(authorDto,author);
        Author updateAuthor =  authorRepo.save(author);
        return AuthorMapper.mapper.toDto(updateAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        Author author = authorRepo.findById(id)
                .orElseThrow(()->new RuntimeException("author.not.found"));
        authorRepo.delete(author);
    }


    @Override
    public AuthorDto getById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        Author author = authorRepo.findById(id)
                .orElseThrow(()->new RuntimeException("author.not.found"));
        return AuthorMapper.mapper.toDto(author);
    }

    @Override
    public AuthorResponseVM getAllAuthors(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Author> authors=authorRepo.findAll(pageable);
        return new AuthorResponseVM(
                AuthorMapper.mapper.toDtoList(authors.getContent()),
                authors.getTotalElements()
        );
    }

    @Override
    public AuthorResponseVM getByName(String name, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Author> authors=authorRepo.getByNameContainingIgnoreCase(name,pageable);
        return new AuthorResponseVM(
                AuthorMapper.mapper.toDtoList(authors.getContent()),
                authors.getTotalElements()
        );
    }
}
