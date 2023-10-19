package me.seongim.jpabook.service.query;

import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.domain.Order;
import me.seongim.jpabook.repository.order.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }
}
