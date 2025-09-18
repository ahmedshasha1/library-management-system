package com.library.controller.vm;

import com.library.dto.PublisherDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PublisherResponseVM {
    private List<PublisherDto> publishers;
    private Long totalPageSize;
}
