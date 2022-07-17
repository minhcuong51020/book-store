package com.example.shopstore.service;

import com.example.shopstore.dto.CartBookDTO;
import com.example.shopstore.dto.OrderDTO;
import com.example.shopstore.dto.OrderDetailsDTO;

import java.util.List;

public interface OrderDetailsService {
    public List<OrderDetailsDTO> addOrderDetails(OrderDTO orderDTO, List<CartBookDTO> cartBookDTOS);

}
