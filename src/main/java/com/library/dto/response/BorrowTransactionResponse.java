package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.dto.BaseEntityDTO;
import com.library.enumration.Transaction;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowTransactionResponse extends BaseEntityDTO {
    private Long memberId;
    private String memberName;

    private Long bookId;
    private String bookName;

    private LocalDateTime borrowDate;
    private Transaction status;

}
