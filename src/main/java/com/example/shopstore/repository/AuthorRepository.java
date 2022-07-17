package com.example.shopstore.repository;

import com.example.shopstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    public List<Author> getAllByIsActive(int isActive);

}
