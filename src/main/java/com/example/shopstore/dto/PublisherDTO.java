package com.example.shopstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {

    private int id;
    private String name;
    private int yearFounded;
    private String description;
    private int isActive;

    public PublisherDTO(String name, int yearFounded, String description) {
        this.name = name;
        this.yearFounded = yearFounded;
        this.description = description;
    }

}
