package com.library.service;

import com.library.controller.vm.BorrowTransactionResponseVM;
import com.library.controller.vm.MemberResponseVM;
import com.library.dto.MemberDto;

public interface MemberService {

    void addNewMember(MemberDto memberDto);
    MemberDto updateMember(MemberDto memberDto);
    void deleteMember(Long id);
    MemberDto getById(Long id);
    MemberDto getByEmail(String email);
    MemberResponseVM getByName(String name,Integer pageNo, Integer pageSize);
    MemberResponseVM getAllMembers(Integer pageNo, Integer pageSize);
    MemberResponseVM getActiveMembers(Integer pageNo, Integer pageSize);

    BorrowTransactionResponseVM getBorrowingHistory(Long memberId,Integer pageNo, Integer pageSize);



}
