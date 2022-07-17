package com.example.shopstore.service;

import com.example.shopstore.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    public AuthorDTO addAuthor(AuthorDTO authorDTO);
    public AuthorDTO updateAuthor(AuthorDTO authorDTO);
    public void deleteAuthor(int id);
    public AuthorDTO getAuthorById(int id);
    public List<AuthorDTO> getAllAuthor();
    public boolean existsAuthorById(int id);
    public List<AuthorDTO> getAllAuthorByIsActive(int isActive);

}
