package com.example.shopstore.service;

import com.example.shopstore.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {

    public PublisherDTO addPublisher(PublisherDTO publisherDTO);
    public PublisherDTO updatePublisher(PublisherDTO publisherDTO);
    public void deletePublisher(int id);
    public PublisherDTO getPublisherById(int id);
    public List<PublisherDTO> getAllPublisher();
    public List<PublisherDTO> getAllPublisherByIsActive(int isActive);

}
