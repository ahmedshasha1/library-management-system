package com.library.controller.vm;

import com.library.dto.response.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CategoryResponseVM {
    private List<CategoryResponse> categories;
    private Long totalPageSize;

}
