package com.library.controller.vm;

import com.library.dto.response.BorrowTransactionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class BorrowTransactionResponseVM {
    private List<BorrowTransactionResponse> transactions;
    private Long totalPageSize;
}
