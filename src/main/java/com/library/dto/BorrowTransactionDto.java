package com.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.enumration.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowTransactionDto extends BaseEntityDTO {
    @NotNull(message = "member.not.found")
    private Long memberId;
    @NotNull(message = "book.not.found")
    private Long bookId;
    @JsonProperty("borrowDate")
    private LocalDateTime borrowDate;
    private Transaction status;
}
