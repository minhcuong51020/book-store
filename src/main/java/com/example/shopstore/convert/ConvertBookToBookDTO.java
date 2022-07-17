package com.example.shopstore.convert;

import com.example.shopstore.dto.BookDTO;
import com.example.shopstore.entity.Book;

public class ConvertBookToBookDTO {
    public static BookDTO convertBookToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO(
                book.getId(), book.getTitle(), book.getIsbn(), book.getPrice(), book.getDiscount(),
                book.getDescription(), book.getIsActive(), book.getCategory().getId(),
                book.getCategory().getName(), book.getAuthor().getId(), book.getAuthor().getName(),
                book.getPublisher().getId(), book.getPublisher().getName(), book.getThumbnail(),
                book.getCreatedAt(), book.getUpdatedAt());
        return bookDTO;
    }

}
