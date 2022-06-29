package com.example.shopstore.repository;

import com.example.shopstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public List<Category> getAllByIsActiveIs(int isActive);

    public Category getCategoryBySlugAndIsActive(String slug, int isActive);

}
