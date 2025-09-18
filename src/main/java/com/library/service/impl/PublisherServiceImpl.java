package com.library.service.impl;

import com.library.controller.vm.PublisherResponseVM;
import com.library.dao.PublisherRepo;
import com.library.dto.PublisherDto;
import com.library.mapper.PublisherMapper;
import com.library.model.Publisher;
import com.library.service.PublisherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private PublisherRepo publisherRepo;
    @Override
    public void addPublisher(PublisherDto publisherDto) {
        if(publisherDto == null){
            throw new RuntimeException("invalid.data");
        }
        if(Objects.nonNull(publisherDto.getId())){
            throw new RuntimeException("id.nonnull");
        }
        publisherRepo.save(PublisherMapper.mapper.toEntity(publisherDto));
    }

    @Override
    public PublisherDto updatePublisher(PublisherDto publisherDto) {
        if (publisherDto == null) {
            throw new RuntimeException("invalid.data");
        }
        if (Objects.isNull(publisherDto.getId())) {
            throw new RuntimeException("id.empty");
        }
        Publisher publisher = publisherRepo.findById(publisherDto.getId())
                .orElseThrow(()->new RuntimeException("publisher.not.found"));

        PublisherMapper.mapper.updatePublisherFromDto(publisherDto,publisher);
        Publisher updatePublisher =  publisherRepo.save(publisher);
        return PublisherMapper.mapper.toDto(updatePublisher);
    }

    @Override
    public void deletePublisher(Long id) {

        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        Publisher publisher = publisherRepo.findById(id)
                .orElseThrow(()->new RuntimeException("publisher.not.found"));
        publisherRepo.delete(publisher);
    }

    @Override
    public PublisherDto getById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        Publisher publisher = publisherRepo.findById(id)
                .orElseThrow(()->new RuntimeException("publisher.not.found"));
        return PublisherMapper.mapper.toDto(publisher);
    }

    @Override
    public PublisherResponseVM getAllPublishers(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Publisher> publishers = publisherRepo.findAll(pageable);
        return new PublisherResponseVM(
                PublisherMapper.mapper.toDtoList(publishers.getContent()),
                publishers.getTotalElements()
        );
    }

    @Override
    public PublisherResponseVM getByName(String name, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("invalid.data");
        }
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Publisher> publishers = publisherRepo.getByNameContainingIgnoreCase(name,pageable);
        return new PublisherResponseVM(
                PublisherMapper.mapper.toDtoList(publishers.getContent()),
                publishers.getTotalElements()
        );
    }
}
