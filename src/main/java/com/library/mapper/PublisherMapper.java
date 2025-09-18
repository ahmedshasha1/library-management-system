package com.library.mapper;

import com.library.dto.PublisherDto;
import com.library.model.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PublisherMapper {
    PublisherMapper mapper= Mappers.getMapper(PublisherMapper.class);

    PublisherDto toDto(Publisher publisher);
    Publisher toEntity(PublisherDto publisherDto);
    List<PublisherDto> toDtoList(List<Publisher> publishers);

    void updatePublisherFromDto(PublisherDto publisherDto, @MappingTarget Publisher publisher);

}
