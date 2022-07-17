package com.example.shopstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int id;
    private String fullName;
    private String phone;
    private String address;
    private double totalPrice;
    private int status;
    private Timestamp createdAt;
    private int userId;
    private List<OrderDetailsDTO> orderDetailsDTOS;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                '}';
    }

    public void setOrderDetailsDTOS(List<OrderDetailsDTO> orderDetailsDTOS) {
        this.orderDetailsDTOS = orderDetailsDTOS;
    }
}
