package com.example.shopstore.service.impl;

import com.example.shopstore.convert.ConvertBookToBookDTO;
import com.example.shopstore.dto.BookDTO;
import com.example.shopstore.dto.OrderDTO;
import com.example.shopstore.dto.OrderDetailsDTO;
import com.example.shopstore.entity.OrderDetails;
import com.example.shopstore.entity.Orders;
import com.example.shopstore.entity.User;
import com.example.shopstore.repository.OrderRepository;
import com.example.shopstore.repository.UserRepository;
import com.example.shopstore.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrdersDAOImpl implements OrdersService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        Optional<User> user = userRepository.findById(orderDTO.getUserId());
        if(user.isPresent()) {
            Orders orders = new Orders();
            orders.setFullName(orderDTO.getFullName());
            orders.setPhone(orderDTO.getPhone());
            orders.setAddress(orderDTO.getAddress());
            orders.setTotalMoney(orderDTO.getTotalPrice());
            orders.setUser(user.get());
        }
        return null;
    }

    @Override
    public OrderDTO getOrderByIdAndUserId(int id, int userId) {
        Orders orders = orderRepository.getOrdersByIdAndUserId(id, userId);
        if(orders == null) {
            return null;
        }
        List<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
        for (OrderDetails od : orders.getOrderDetails()) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    od.getId(), od.getPrice(), od.getNum(), od.getTotalMoney(), od.getOrders().getId(),
                    ConvertBookToBookDTO.convertBookToBookDTO(od.getBook())
            );
            orderDetailsDTOS.add(orderDetailsDTO);
        }
        OrderDTO orderDTO = new OrderDTO(
                orders.getId(), orders.getFullName(), orders.getPhone(), orders.getAddress(),
                orders.getTotalMoney(), orders.getStatus(), orders.getCreatedAt(),
                orders.getUser().getId(), orderDetailsDTOS
        );
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findAllOrdersByUserIdAndStatus(int id, int status) {
        List<Orders> orders = orderRepository.findAllByUserIdAndStatusOrderByIdDesc(id, status);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Orders o : orders) {
            List<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
            for (OrderDetails od : o.getOrderDetails()) {
                OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                        od.getId(), od.getPrice(), od.getNum(), od.getTotalMoney(), od.getOrders().getId(),
                        ConvertBookToBookDTO.convertBookToBookDTO(od.getBook())
                );
                orderDetailsDTOS.add(orderDetailsDTO);
            }
            OrderDTO orderDTO = new OrderDTO(
                    o.getId(), o.getFullName(), o.getPhone(), o.getAddress(), o.getTotalMoney(), o.getStatus(),
                    o.getCreatedAt(), o.getUser().getId(), orderDetailsDTOS
            );
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    public List<OrderDTO> findAllOrdersByStatus(int status) {
        List<Orders> ordersList = orderRepository.findAllByStatus(status);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Orders o : ordersList) {
            List<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
            for (OrderDetails od : o.getOrderDetails()) {
                OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                        od.getId(), od.getPrice(), od.getNum(), od.getTotalMoney(), od.getOrders().getId(),
                        ConvertBookToBookDTO.convertBookToBookDTO(od.getBook())
                );
                orderDetailsDTOS.add(orderDetailsDTO);
            }
            OrderDTO orderDTO = new OrderDTO(
                    o.getId(), o.getFullName(), o.getPhone(), o.getAddress(), o.getTotalMoney(), o.getStatus(),
                    o.getCreatedAt(), o.getUser().getId(), orderDetailsDTOS
            );
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    public int setStatusOrderById(int status, int id) {
        int result = orderRepository.setStatusOrdersById(status, id);
        return result;
    }
}
