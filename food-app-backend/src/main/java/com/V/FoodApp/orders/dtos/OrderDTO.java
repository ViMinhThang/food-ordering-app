package com.V.FoodApp.orders.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.V.FoodApp.auth_users.dtos.UserDTO;
import com.V.FoodApp.enums.OrderStatus;
import com.V.FoodApp.enums.PaymentStatus;
import jdk.jfr.DataAmount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@DataAmount
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {

    private Long id;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private UserDTO user;

    private List<OrderItemDTO> orderItem;
}
