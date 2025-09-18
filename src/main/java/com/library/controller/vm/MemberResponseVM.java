package com.library.controller.vm;

import com.library.dto.MemberDto;
import com.library.dto.response.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class MemberResponseVM {
    private List<MemberDto> members;
    private Long totalPageSize;
}
