package com.example.shopstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartBookDTO {
    private int id;
    private int quantity;
    private BookDTO bookDTO;
    private CartDTO cartDTO;

}
