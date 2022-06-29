package com.example.shopstore.service;

import com.example.shopstore.dto.CartDTO;

public interface CartService {

    public CartDTO addCart(CartDTO cartDTO);

    public CartDTO getCartByUserId(int id);

    public boolean existsCartByUserId(int id);

    public CartDTO getCartById(int id);

}
