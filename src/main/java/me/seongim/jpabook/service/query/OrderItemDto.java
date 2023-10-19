package me.seongim.jpabook.service.query;

import lombok.Data;
import me.seongim.jpabook.domain.OrderItem;

@Data
public class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}