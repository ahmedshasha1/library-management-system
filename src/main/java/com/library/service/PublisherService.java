package com.library.service;

import com.library.controller.vm.PublisherResponseVM;
import com.library.dto.PublisherDto;

public interface PublisherService {

    void addPublisher(PublisherDto publisherDto);
    PublisherDto updatePublisher(PublisherDto publisherDto);
    void deletePublisher(Long id);
    PublisherDto getById(Long id);
    PublisherResponseVM getAllPublishers(Integer pageNo, Integer pageSize);
    PublisherResponseVM getByName(String name,Integer pageNo, Integer pageSize);
}
