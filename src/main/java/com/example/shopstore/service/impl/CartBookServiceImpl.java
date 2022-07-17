package com.example.shopstore.service.impl;

import com.example.shopstore.convert.ConvertBookToBookDTO;
import com.example.shopstore.dto.BookDTO;
import com.example.shopstore.dto.CartBookDTO;
import com.example.shopstore.dto.CartDTO;
import com.example.shopstore.entity.Book;
import com.example.shopstore.entity.Cart;
import com.example.shopstore.entity.CartBook;
import com.example.shopstore.repository.BooksRepository;
import com.example.shopstore.repository.CartBookRepository;
import com.example.shopstore.repository.CartRepository;
import com.example.shopstore.service.CartBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartBookServiceImpl implements CartBookService {
    @Autowired
    private CartBookRepository cartBookRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public CartBookDTO addBookToCart(CartBookDTO cartBookDTO) {
        CartBook cartBook = new CartBook();
        Optional<Cart> optionalCart = cartRepository.findById(cartBookDTO.getCartDTO().getId());
        Optional<Book> optionalBook = booksRepository.findById(cartBookDTO.getBookDTO().getId());
        if(optionalCart.isPresent() && optionalBook.isPresent()) {
            cartBook.setCart(optionalCart.get());
            cartBook.setBook(optionalBook.get());
            cartBook.setNum(cartBookDTO.getQuantity());
            cartBookRepository.save(cartBook);
            cartBookDTO.setId(cartBook.getId());
            return cartBookDTO;
        }
        return null;
    }

    @Override
    public CartBookDTO updateCartBook(CartBookDTO cartBookDTO) {
        CartBook cartBook = cartBookRepository.getByCart_IdAndBook_Id(
                cartBookDTO.getCartDTO().getId(), cartBookDTO.getBookDTO().getId());
        if(cartBook.getId() != 0) {
            cartBook.setNum(cartBook.getNum() + cartBookDTO.getQuantity());
            cartBookRepository.save(cartBook);
            cartBookDTO.setId(cartBook.getId());
            return cartBookDTO;
        }
        return null;
    }

    @Override
    public List<CartBookDTO> getAllCartByCart_Id(int cartId) {
        List<CartBook> cartBooks = cartBookRepository.getAllByCart_Id(cartId);
        List<CartBookDTO> cartBookDTOS = new ArrayList<>();
        for (CartBook c : cartBooks) {
            CartDTO cartDTO = new CartDTO(
                    c.getCart().getId(), c.getCart().getUser().getId()
            );
            BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(c.getBook());
            CartBookDTO cartBookDTO = new CartBookDTO(
                    c.getId(), c.getNum(), bookDTO, cartDTO
            );
            cartBookDTOS.add(cartBookDTO);
        }
        return cartBookDTOS;
    }

    @Override
    public boolean existsByCartIdAndBookId(int cartId, int bookId) {
        return cartBookRepository.existsByCart_IdAndBook_Id(cartId, bookId);
    }

    @Override
    public void deleteCartIdAndBookId(int cartId, int bookId) {
        cartBookRepository.deleteByCart_IdAndBook_Id(cartId, bookId);
    }

    @Override
    public CartBookDTO minusBookOnCartBook(CartBookDTO cartBookDTO) {
        CartBook cartBook = cartBookRepository.getByCart_IdAndBook_Id(
                cartBookDTO.getCartDTO().getId(), cartBookDTO.getBookDTO().getId());
        if(cartBook.getId() != 0) {
            if(cartBook.getNum() - 1 != 0) {
                int num = cartBook.getNum() - 1;
                cartBook.setNum(num);
                cartBookRepository.save(cartBook);
                cartBookDTO.setId(cartBook.getId());
            } else {
                deleteCartIdAndBookId(cartBook.getCart().getId(), cartBook.getBook().getId());
                cartBookDTO = new CartBookDTO();
            }
            return cartBookDTO;
        }
        return null;
    }

    @Override
    public CartBookDTO incrementBookOnCartBook(CartBookDTO cartBookDTO) {
        CartBook cartBook = cartBookRepository.getByCart_IdAndBook_Id(
                cartBookDTO.getCartDTO().getId(), cartBookDTO.getBookDTO().getId());
        if(cartBook.getId() != 0) {
            int num = cartBook.getNum() + 1;
            cartBook.setNum(num);
            cartBookRepository.save(cartBook);
            cartBookDTO.setId(cartBook.getId());
            return cartBookDTO;
        }
        return null;
    }
}
