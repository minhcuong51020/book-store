package com.example.shopstore.repository;

import com.example.shopstore.entity.Book;
import com.example.shopstore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {

    public List<Book> getAllByIsActiveOrderByIdDesc(int isActive);

    public List<Book> findTop4ByDiscountGreaterThanAndIsActiveEqualsOrderByIdDesc(double discount, int isActive);

    public List<Book> findTop4ByIsActiveOrderByIdDesc(int isActive);

    public List<Book> findAllByDiscountGreaterThanOrderByIdDesc(double discount);

    @Query(value = "SELECT b FROM Book b WHERE b.category.slug = ?1 AND b.isActive = ?2")
    public Page<Book> findAllByCategorySlugAndIsActive(String slug, int isActive, Pageable pageable);

    public Book getBookByIdAndIsActive(int id, int isActive);

}
