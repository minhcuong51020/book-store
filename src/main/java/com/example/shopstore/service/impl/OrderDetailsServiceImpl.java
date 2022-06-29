package com.example.shopstore.service.impl;

import com.example.shopstore.convert.ConvertBookToBookDTO;
import com.example.shopstore.dto.BookDTO;
import com.example.shopstore.dto.CartBookDTO;
import com.example.shopstore.dto.OrderDTO;
import com.example.shopstore.dto.OrderDetailsDTO;
import com.example.shopstore.entity.Book;
import com.example.shopstore.entity.OrderDetails;
import com.example.shopstore.entity.Orders;
import com.example.shopstore.entity.User;
import com.example.shopstore.repository.*;
import com.example.shopstore.service.OrderDetailsService;
import com.example.shopstore.service.OrdersService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private CartBookRepository cartBookRepository;


    @Override
    public List<OrderDetailsDTO> addOrderDetails(OrderDTO orderDTO, List<CartBookDTO> cartBookDTOS) {
        if(cartBookDTOS.size() <= 0) {
            return null;
        }
        List<OrderDetails> listOrdersDetails = new ArrayList<>();
        Optional<User> user = userRepository.findById(orderDTO.getUserId());
        //create order
        Orders orders = new Orders();
        orders.setFullName(orderDTO.getFullName());
        orders.setPhone(orderDTO.getPhone());
        orders.setAddress(orderDTO.getAddress());
        orders.setTotalMoney(orderDTO.getTotalPrice());
        orders.setStatus(0);
        orders.setUser(user.get());
        orderRepository.save(orders);
        //all id cart_book
        List<Integer> cartBookIds = new ArrayList<>();
        for (CartBookDTO cartBookDTO : cartBookDTOS) {
            Book book = bookRepository.getBookByIdAndIsActive(cartBookDTO.getBookDTO().getId(), 1);
            OrderDetails orderDetails = new OrderDetails();
            double price = cartBookDTO.getBookDTO().getPrice() * (100 - cartBookDTO.getBookDTO().getDiscount()) / 100;
            double totalPrice = price * cartBookDTO.getQuantity();
            orderDetails.setPrice(price);
            orderDetails.setNum(cartBookDTO.getQuantity());
            orderDetails.setTotalMoney(totalPrice);
            orderDetails.setBook(book);
            orderDetails.setOrders(orders);
            listOrdersDetails.add(orderDetails);
            cartBookIds.add(cartBookDTO.getId());
        }
        Optional<List<OrderDetails>> optionalOrderDetails = Optional.ofNullable(orderDetailsRepository.saveAll(listOrdersDetails));
        if(!optionalOrderDetails.isPresent()) {
            orderRepository.delete(orders);
            return null;
        }
        cartBookRepository.deleteAllById(cartBookIds);
        orderDTO.setId(orders.getId());
        orderDTO.setCreatedAt(orders.getCreatedAt());
        List<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
        for (OrderDetails orderDetails : optionalOrderDetails.get()) {
            BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(orderDetails.getBook());
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderDetails.getId(), orderDetails.getPrice(), orderDetails.getNum(), orderDetails.getTotalMoney(),
                    orderDetails.getOrders().getId(), bookDTO
            );
            orderDetailsDTOS.add(orderDetailsDTO);
        }
        return orderDetailsDTOS;
    }
}
