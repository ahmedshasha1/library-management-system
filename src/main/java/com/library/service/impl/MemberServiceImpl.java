package com.library.service.impl;

import com.library.controller.vm.BorrowTransactionResponseVM;
import com.library.controller.vm.MemberResponseVM;
import com.library.dao.BorrowTransactionRepo;
import com.library.dao.MemberRepo;
import com.library.dto.MemberDto;
import com.library.mapper.BorrowTransactionMapper;
import com.library.mapper.MemberMapper;
import com.library.model.BorrowTransaction;
import com.library.model.Member;
import com.library.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private BorrowTransactionRepo borrowTransactionRepo;

    @Override
    public void addNewMember(MemberDto memberDto) {
        if(memberDto == null){
            throw new RuntimeException("invalid.data");
        }
        if(Objects.nonNull(memberDto.getId())){
            throw new RuntimeException("id.nonnull");
        }
        memberRepo.save(MemberMapper.mapper.toEntity(memberDto));
    }
    @Override
    public MemberDto updateMember(MemberDto memberDto) {
        if(memberDto == null){
            throw new RuntimeException("invalid.data");
        }
        if(Objects.isNull(memberDto.getId())){
            throw new RuntimeException("id.empty");
        }
        Member member = memberRepo.findById(memberDto.getId())
                .orElseThrow(()-> new RuntimeException("member.not.found"));
        MemberMapper.mapper.updateMemberFromDto(memberDto,member);
        Member updateMember = memberRepo.save(member);
        return MemberMapper.mapper.toDto(updateMember);
    }

    @Override
    public void deleteMember(Long id) {
        if(Objects.isNull(id)){
            throw new RuntimeException("id.empty");
        }
        Member member = memberRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("member.not.found"));
        memberRepo.delete(member);
    }

    @Override
    public MemberDto getById(Long id) {
        if(Objects.isNull(id)){
            throw new RuntimeException("id.empty");
        }
        Member member = memberRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("member.not.found"));
        return MemberMapper.mapper.toDto(member);
    }

    @Override
    public MemberDto getByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new RuntimeException("invalid.data");
        }
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("member.not.found"));
        return MemberMapper.mapper.toDto(member);
    }
    @Override
    public MemberResponseVM getByName(String name,Integer pageNo, Integer pageSize) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Member> member = memberRepo.findByNameContainingIgnoreCase(name,pageable);

        return new MemberResponseVM(
                MemberMapper.mapper.toDtoList(member.getContent()),
                member.getTotalElements()
        );
    }
    @Override
    public MemberResponseVM getAllMembers(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Member> member = memberRepo.findAll(pageable);

        return new MemberResponseVM(
                MemberMapper.mapper.toDtoList(member.getContent()),
                member.getTotalElements()
        );
    }

    @Override
    public MemberResponseVM getActiveMembers(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Member> member = memberRepo.findByActive(true,pageable);

        return new MemberResponseVM(
                MemberMapper.mapper.toDtoList(member.getContent()),
                member.getTotalElements()
        );
    }

    @Override
    public BorrowTransactionResponseVM getBorrowingHistory(Long memberId, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(memberId)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Member member=memberRepo.findById(memberId)
                .orElseThrow(()-> new RuntimeException("member.not.found"));
        Page<BorrowTransaction> borrowTransactions = borrowTransactionRepo.findByMemberId(member.getId(),pageable);
        return new BorrowTransactionResponseVM(
                BorrowTransactionMapper.mapper.toResponseList(borrowTransactions.getContent()),
                borrowTransactions.getTotalElements()
        );
    }
}
