package com.library.mapper;

import com.library.dto.MemberDto;
import com.library.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberMapper mapper= Mappers.getMapper(MemberMapper.class);

    MemberDto toDto(Member member);
    Member toEntity(MemberDto memberDto);
    List<MemberDto> toDtoList(List<Member> members);

    void updateMemberFromDto(MemberDto memberDto, @MappingTarget Member member);


}
