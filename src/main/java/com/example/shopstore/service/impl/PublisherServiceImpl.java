package com.example.shopstore.service.impl;

import com.example.shopstore.dto.PublisherDTO;
import com.example.shopstore.entity.Publisher;
import com.example.shopstore.repository.PublisherRepository;
import com.example.shopstore.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public PublisherDTO addPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());
        publisher.setYearFounded(publisherDTO.getYearFounded());
        publisher.setDescription(publisherDTO.getDescription());
        Optional<Publisher> optional = Optional.ofNullable(publisherRepository.save(publisher));
        if(!optional.isPresent()) {
            return null;
        }
        publisher = optional.get();
        publisherDTO.setId(publisher.getId());
        publisherDTO.setIsActive(publisher.getIsActive());
        return publisherDTO;
    }

    @Override
    public PublisherDTO updatePublisher(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher(
                publisherDTO.getId(), publisherDTO.getName(), publisherDTO.getYearFounded(),
                publisherDTO.getDescription(), publisherDTO.getIsActive()
        );
        Optional<Publisher> optional = Optional.ofNullable(publisherRepository.save(publisher));
        if(!optional.isPresent()) {
            return null;
        }
        return publisherDTO;
    }

    @Override
    public void deletePublisher(int id) {

    }

    @Override
    public PublisherDTO getPublisherById(int id) {
        Optional<Publisher> optional = publisherRepository.findById(id);
        if(!optional.isPresent()) {
            return null;
        }
        Publisher publisher = optional.get();
        PublisherDTO publisherDTO = new PublisherDTO(
                publisher.getId(), publisher.getName(),
                publisher.getYearFounded(), publisher.getDescription(),
                publisher.getIsActive()
        );
        return publisherDTO;
    }

    @Override
    public List<PublisherDTO> getAllPublisher() {
        List<Publisher> publishers = publisherRepository.findAll();
        List<PublisherDTO> publisherDTOS = new ArrayList<>();
        for (Publisher publisher : publishers) {
            PublisherDTO publisherDTO = new PublisherDTO(
                    publisher.getId(), publisher.getName(),
                    publisher.getYearFounded(), publisher.getDescription(),
                    publisher.getIsActive()
            );
            publisherDTOS.add(publisherDTO);
        }
        return publisherDTOS;
    }

    @Override
    public List<PublisherDTO> getAllPublisherByIsActive(int isAvtive) {
        List<Publisher> publishers = publisherRepository.getAllByIsActive(isAvtive);
        List<PublisherDTO> publisherDTOS = new ArrayList<>();
        for (Publisher publisher : publishers) {
            PublisherDTO publisherDTO = new PublisherDTO(
                    publisher.getId(), publisher.getName(),
                    publisher.getYearFounded(), publisher.getDescription(),
                    publisher.getIsActive()
            );
            publisherDTOS.add(publisherDTO);
        }
        return publisherDTOS;
    }
}
