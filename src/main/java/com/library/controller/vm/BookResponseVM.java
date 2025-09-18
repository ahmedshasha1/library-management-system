package com.library.controller.vm;

import com.library.dto.response.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class BookResponseVM {
    private List<BookResponse> books;
    private Long totalPageSize;
}
