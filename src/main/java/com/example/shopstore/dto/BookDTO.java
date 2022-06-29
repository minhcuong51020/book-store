package com.example.shopstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private int id;

    private String title;

    private String isbn;

    private double price;

    private double discount;

    private String description;

    private int isActive;

    private int categoryId;

    private String categoryName;

    private int authorId;

    private String authorName;

    private int publisherId;

    private String publisherName;

    private MultipartFile file;

    private String thumbnail;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public BookDTO(int id, String title, String isbn, double price, double discount,
                   String description, int isActive, int categoryId,
                   String categoryName, int authorId, String authorName,
                   int publisherId, String publisherName, String thumbnail,
                   Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.discount = discount;
        this.description = description;
        this.isActive = isActive;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.authorId = authorId;
        this.authorName = authorName;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.thumbnail = thumbnail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", publisherId=" + publisherId +
                ", publisherName='" + publisherName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
