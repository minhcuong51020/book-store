package com.example.shopstore.service;

import com.example.shopstore.dto.BookDTO;
import com.example.shopstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    public BookDTO addBook(BookDTO bookDTO);
    public BookDTO updateBook(BookDTO bookDTO);
    public void deleteBook(int id);
    public BookDTO getBookById(int id);
    public List<BookDTO> getAllBook();
    public List<BookDTO> getAllBookByIsActive(int isActive);
    public boolean existBookById(int id);
    public List<BookDTO> findTopBookByDiscount(double discount, int isActive);
    public List<BookDTO> findAllByDiscountGreaterThan(double discount);
    public List<BookDTO> findTopBookRandom(int isAvtive);
    public List<BookDTO> findTopBookNew(int isActive);
    public Page<BookDTO> findAllBookByCategoryAndIsActive(String slugCategory, int isActive, int pageNo, int pageSize);
    public BookDTO getBookByIdAndIsActive(int id, int isActive);

}
