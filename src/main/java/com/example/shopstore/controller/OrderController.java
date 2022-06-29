package com.example.shopstore.controller;

import com.example.shopstore.dto.*;
import com.example.shopstore.service.CartBookService;
import com.example.shopstore.service.CartService;
import com.example.shopstore.service.OrderDetailsService;
import com.example.shopstore.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartBookService cartBookService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrdersService ordersService;

    @GetMapping(value = {"/checkout"})
    public String checkoutView(ModelMap modelMap, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        CartDTO cartDTO = cartService.getCartByUserId(userDTO.getId());
        List<CartBookDTO> cartBookDTOS = cartBookService.getAllCartByCart_Id(cartDTO.getId());
        double totalPrice = 0;
        for (CartBookDTO cartBookDTO : cartBookDTOS) {
            double price = cartBookDTO.getBookDTO().getPrice() * (100 - cartBookDTO.getBookDTO().getDiscount()) / 100;
            totalPrice += (price * cartBookDTO.getQuantity());
        }
        modelMap.addAttribute("cartBookDTOS", cartBookDTOS);
        modelMap.addAttribute("totalPrice", totalPrice);
        modelMap.addAttribute("userDTO", userDTO);
        return "/user/checkout";
    }

    @PostMapping(value = {"/checkout"})
    public String checkout(ModelMap modelMap, HttpSession session, @ModelAttribute("orderDTO") OrderDTO orderDTO) {
        UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
        orderDTO.setUserId(userDTO.getId());
        CartDTO cartDTO = cartService.getCartByUserId(userDTO.getId());
        List<CartBookDTO> cartBookDTOS = cartBookService.getAllCartByCart_Id(cartDTO.getId());
        List<OrderDetailsDTO> orderDetailsDTOS = orderDetailsService.addOrderDetails(orderDTO, cartBookDTOS);
        if(orderDetailsDTOS == null) {
            return "redirect: /orderFail";
        }
        modelMap.addAttribute("orderDTO", orderDTO);
        modelMap.addAttribute("orderDetailsDTOS", orderDetailsDTOS);
        return "/user/orderSuccess";
    }

    @GetMapping(value = {"/orderSuccess"})
    public String test() {
        return "/user/orderSuccess";
    }

    @GetMapping(value = {"/orderFail"})
    public String test2() {
        return "404";
    }

    @GetMapping(value = {"/user/order/success"})
    public String orderUserSuccessView(ModelMap modelMap, HttpSession httpSession) {
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("currentUser");
        List<OrderDTO> orderDTOS = ordersService.findAllOrdersByUserIdAndStatus(userDTO.getId(), 1);
        System.out.println(orderDTOS.size());
        modelMap.addAttribute("orderDTOS", orderDTOS);
        return "/user/orderInfoSuccess";
    }

    @GetMapping(value = {"/user/order/pending"})
    public String orderUserPendingView(ModelMap modelMap, HttpSession httpSession) {
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("currentUser");
        List<OrderDTO> orderDTOS = ordersService.findAllOrdersByUserIdAndStatus(userDTO.getId(), 0);
        System.out.println(orderDTOS.size());
        modelMap.addAttribute("orderDTOS", orderDTOS);
        return "/user/orderInfoPending";
    }

    @GetMapping(value = {"/user/order/details/{id}"})
    public String orderDetailsView(ModelMap modelMap, HttpSession httpSession, @PathVariable("id") int id) {
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("currentUser");
        OrderDTO orderDTO = ordersService.getOrderByIdAndUserId(id, userDTO.getId());
        if(orderDTO == null) {
            modelMap.addAttribute("msgError", "Không có đơn hàng này");
        } else {
            modelMap.addAttribute("orderDTO", orderDTO);
        }
        return "/user/orderDetails";
    }

    @GetMapping(value = {"/admin/orderPending"})
    public String orderPendingView(ModelMap modelMap) {
        List<OrderDTO> orderDTOS = ordersService.findAllOrdersByStatus(0);
        modelMap.addAttribute("orderDTOS", orderDTOS);
        modelMap.addAttribute("orderDTO", new OrderDTO());
        return "admin/orderListPending";
    }

    @GetMapping(value = {"/admin/orderSuccess"})
    public String orderSuccessView(ModelMap modelMap) {
        List<OrderDTO> orderDTOS = ordersService.findAllOrdersByStatus(1);
        modelMap.addAttribute("orderDTOS", orderDTOS);
        return "admin/orderListSuccess";
    }

    @PostMapping(value = {"/admin/orders/success"})
    public String setOrderSuccess(ModelMap modelMap, @ModelAttribute("orderDTO") OrderDTO orderDTO) {
        ordersService.setStatusOrderById(orderDTO.getStatus(), orderDTO.getId());
        return "redirect:/admin/orderPending";
    }
}
