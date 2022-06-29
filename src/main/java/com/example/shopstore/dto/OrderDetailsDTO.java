package com.example.shopstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {

    private int id;
    private double price;
    private int quantity;
    private double totalPrice;
    private int orderId;
    private BookDTO bookDTO;

}
