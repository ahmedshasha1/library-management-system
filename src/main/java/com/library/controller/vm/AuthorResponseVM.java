package com.library.controller.vm;

import com.library.dto.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AuthorResponseVM {
    private List<AuthorDto> authors;
    private Long totalPageSize;
}
