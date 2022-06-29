package com.example.shopstore.repository;

import com.example.shopstore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    public Cart getCartByUser_Id(int id);

    public boolean existsCartByUser_Id(int id);

}
