package com.example.shopstore.repository;

import com.example.shopstore.entity.CartBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartBookRepository extends JpaRepository<CartBook, Integer> {

    public List<CartBook> getAllByCart_Id(int cartId);

    public boolean existsByCart_IdAndBook_Id(int cartId, int bookId);

    public CartBook getByCart_IdAndBook_Id(int cartId, int bookId);

    public void deleteByCart_IdAndBook_Id(int cartId, int bookId);

}
