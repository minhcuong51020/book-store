package com.example.shopstore.service;

import com.example.shopstore.dto.CartBookDTO;

import java.util.List;

public interface CartBookService {

    public CartBookDTO addBookToCart(CartBookDTO cartBookDTO);

    public CartBookDTO updateCartBook(CartBookDTO cartBookDTO);

    public List<CartBookDTO> getAllCartByCart_Id(int cartId);

    public boolean existsByCartIdAndBookId(int cartId, int bookId);

    public void deleteCartIdAndBookId(int cartId, int bookId);

    public CartBookDTO minusBookOnCartBook(CartBookDTO cartBookDTO);

    public CartBookDTO incrementBookOnCartBook(CartBookDTO cartBookDTO);

}
