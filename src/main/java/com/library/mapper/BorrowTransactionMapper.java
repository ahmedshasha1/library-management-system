package com.library.mapper;

import com.library.dto.BorrowTransactionDto;
import com.library.dto.response.BorrowTransactionResponse;
import com.library.model.BorrowTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BorrowTransactionMapper {
    BorrowTransactionMapper mapper= Mappers.getMapper(BorrowTransactionMapper.class);

    BorrowTransactionDto toDto(BorrowTransaction borrowTransaction);
    BorrowTransaction toEntity(BorrowTransactionDto borrowTransactionDto);
    List<BorrowTransactionDto> toDtoList(List<BorrowTransaction> borrowTransactions);

    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "member.name", target = "memberName")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.name", target = "bookName")
    BorrowTransactionResponse toResponseDto(BorrowTransaction transaction);

    List<BorrowTransactionResponse> toResponseList(List<BorrowTransaction> borrowTransactions);


}
