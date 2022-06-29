package com.example.shopstore.service.impl;

import com.example.shopstore.dto.CartDTO;
import com.example.shopstore.entity.Cart;
import com.example.shopstore.entity.User;
import com.example.shopstore.repository.CartRepository;
import com.example.shopstore.repository.UserRepository;
import com.example.shopstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public CartDTO addCart(CartDTO cartDTO) {
        Optional<User> optionalUser = userRepository.findById(cartDTO.getIdUser());
        Cart cart = new Cart();
        cart.setUser(optionalUser.get());
        cartRepository.save(cart);
        cartDTO.setId(cart.getId());
        return cartDTO;
    }

    @Override
    public CartDTO getCartByUserId(int id) {
        Optional<Cart> cart = Optional.ofNullable(cartRepository.getCartByUser_Id(id));
        if(cart.isPresent()) {
            CartDTO cartDTO = new CartDTO(
                    cart.get().getId(), cart.get().getUser().getId()
            );
            return cartDTO;
        }
        return null;
    }

    @Override
    public boolean existsCartByUserId(int id) {
        return cartRepository.existsCartByUser_Id(id);
    }

    @Override
    public CartDTO getCartById(int id) {
        Optional<Cart> optional = cartRepository.findById(id);
        if (optional.isPresent()) {
            Cart cart = optional.get();
            CartDTO cartDTO = new CartDTO(cart.getId(), cart.getUser().getId());
            return cartDTO;
        }
        return null;
    }
}
