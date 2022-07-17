package com.example.shopstore.service;

import com.example.shopstore.dto.OrderDTO;

import java.util.List;

public interface OrdersService {
    public OrderDTO addOrder(OrderDTO orderDTO);

    public OrderDTO getOrderByIdAndUserId(int id, int userId);

    public List<OrderDTO> findAllOrdersByUserIdAndStatus(int id, int status);

    public List<OrderDTO> findAllOrdersByStatus(int status);

    public int setStatusOrderById(int status, int id);

}
