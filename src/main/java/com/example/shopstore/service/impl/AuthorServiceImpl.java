package com.example.shopstore.service.impl;

import com.example.shopstore.dto.AuthorDTO;
import com.example.shopstore.entity.Author;
import com.example.shopstore.repository.AuthorRepository;
import com.example.shopstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public AuthorDTO addAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        Optional<Author> optional = Optional.ofNullable(authorRepository.save(author));
        if(!optional.isPresent()) {
            return null;
        }
        authorDTO.setId(author.getId());
        authorDTO.setIsActive(author.getIsActive());
        return authorDTO;
    }

    @Override
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        Author author = new Author(
                authorDTO.getId(), authorDTO.getName(), authorDTO.getIsActive()
        );
        Optional<Author> optional = Optional.ofNullable(authorRepository.save(author));
        if(!optional.isPresent()) {
            return null;
        }
        return authorDTO;
    }

    @Override
    public void deleteAuthor(int id) {

    }

    @Override
    public AuthorDTO getAuthorById(int id) {
        Optional<Author> optional = authorRepository.findById(id);
        if(!optional.isPresent()) {
            return null;
        }
        Author author = optional.get();
        AuthorDTO authorDTO = new AuthorDTO(
                author.getId(), author.getName(), author.getIsActive()
        );
        return authorDTO;
    }

    @Override
    public List<AuthorDTO> getAllAuthor() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        for (Author author : authors) {
            AuthorDTO authorDTO = new AuthorDTO(
                    author.getId(), author.getName(), author.getIsActive()
            );
            authorDTOS.add(authorDTO);
        }
        return authorDTOS;
    }

    @Override
    public boolean existsAuthorById(int id) {
        return authorRepository.existsById(id);
    }

    @Override
    public List<AuthorDTO> getAllAuthorByIsActive(int isActive) {
        List<Author> authors = authorRepository.getAllByIsActive(isActive);
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        for (Author author : authors) {
            AuthorDTO authorDTO = new AuthorDTO(
                    author.getId(), author.getName(), author.getIsActive()
            );
            authorDTOS.add(authorDTO);
        }
        return authorDTOS;
    }
}
