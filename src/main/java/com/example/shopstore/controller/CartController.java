package com.example.shopstore.controller;

import com.example.shopstore.dto.CartBookDTO;
import com.example.shopstore.dto.CartDTO;
import com.example.shopstore.dto.UserDTO;
import com.example.shopstore.service.CartBookService;
import com.example.shopstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartBookService cartBookService;

    @GetMapping("/cart")
    public String cartView(ModelMap modelMap, HttpSession httpSession) {
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("currentUser");
        CartDTO cartDTO = new CartDTO();
        if(cartService.existsCartByUserId(userDTO.getId())) {
            cartDTO = cartService.getCartByUserId(userDTO.getId());
        } else {
            cartDTO.setIdUser(userDTO.getId());
            cartDTO = cartService.addCart(cartDTO);
        }
        List<CartBookDTO> cartBookDTOS = cartBookService.getAllCartByCart_Id(cartDTO.getId());
        double totalPrice = 0;
        for (CartBookDTO cartBookDTO : cartBookDTOS) {
            double price = cartBookDTO.getBookDTO().getPrice() * (100 - cartBookDTO.getBookDTO().getDiscount()) / 100;
            totalPrice += (price * cartBookDTO.getQuantity());
        }
        modelMap.addAttribute("cartBookDTOS", cartBookDTOS);
        modelMap.addAttribute("totalPrice", totalPrice);
        return "user/cart";
    }

    @PostMapping(value = {"/cart/addBook"})
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartBookDTO> addBookToCart(@RequestBody CartBookDTO cartBookDTO, HttpSession httpSession) {
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("currentUser");
        CartDTO cartDTO = new CartDTO();
        if(cartService.existsCartByUserId(userDTO.getId())) {
            cartDTO = cartService.getCartByUserId(userDTO.getId());
        } else {
            cartDTO.setIdUser(userDTO.getId());
            cartDTO = cartService.addCart(cartDTO);
        }
        cartBookDTO.setCartDTO(cartDTO);
        if(cartBookService.existsByCartIdAndBookId(cartDTO.getId(), cartBookDTO.getBookDTO().getId())) {
            cartBookDTO = cartBookService.updateCartBook(cartBookDTO);
        } else {
            cartBookDTO = cartBookService.addBookToCart(cartBookDTO);
        }
        Optional<Integer> cartBookId = Optional.ofNullable(cartBookDTO.getId());
        if(!cartBookId.isPresent()) {
            return new ResponseEntity<>(cartBookDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartBookDTO, HttpStatus.OK);
    }

    @PostMapping(value = {"/cart/updateBook/minus"})
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CartBookDTO>> minusBookOnCart(@RequestBody CartBookDTO cartBookDTO, HttpSession httpSession) {
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("currentUser");
        CartDTO cartDTO = cartService.getCartByUserId(userDTO.getId());
        cartBookDTO.setCartDTO(cartDTO);
        cartBookService.minusBookOnCartBook(cartBookDTO);
        List<CartBookDTO> cartBookDTOS = cartBookService.getAllCartByCart_Id(cartDTO.getId());
        return new ResponseEntity<>(cartBookDTOS, HttpStatus.OK);
    }

    @PostMapping(value = {"/cart/updateBook/increment"})
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CartBookDTO>> incrementBookOnCart(@RequestBody CartBookDTO cartBookDTO, HttpSession httpSession) {
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("currentUser");
        CartDTO cartDTO = cartService.getCartByUserId(userDTO.getId());
        cartBookDTO.setCartDTO(cartDTO);
        cartBookService.incrementBookOnCartBook(cartBookDTO);
        List<CartBookDTO> cartBookDTOS = cartBookService.getAllCartByCart_Id(cartDTO.getId());
        return new ResponseEntity<>(cartBookDTOS, HttpStatus.OK);
    }

    @PostMapping(value = {"/cart/deleteBook"})
    public ResponseEntity<List<CartBookDTO>> deleteBookFromCart(@RequestBody CartBookDTO cartBookDTO, HttpSession httpSession) {
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("currentUser");
        CartDTO cartDTO = cartService.getCartByUserId(userDTO.getId());
        cartBookService.deleteCartIdAndBookId(cartDTO.getId(), cartBookDTO.getBookDTO().getId());
        List<CartBookDTO> cartBookDTOS = cartBookService.getAllCartByCart_Id(cartDTO.getId());
        return new ResponseEntity<>(cartBookDTOS, HttpStatus.OK);
    }

}
