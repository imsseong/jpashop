package me.seongim.jpabook.service.query;

import lombok.Data;
import me.seongim.jpabook.domain.Address;
import me.seongim.jpabook.domain.Order;
import me.seongim.jpabook.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
        //order.getOrderItems().stream().forEach(o -> o.getItem().getName());
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(toList());
    }
}