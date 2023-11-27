package me.seongim.jpabook.service;

import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.domain.*;
import me.seongim.jpabook.domain.item.Item;
import me.seongim.jpabook.repository.ItemRepository;
import me.seongim.jpabook.repository.MemberJRepositoryOld;
import me.seongim.jpabook.repository.order.OrderRepository;
import me.seongim.jpabook.repository.order.OrderSearch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberJRepositoryOld memberJRepositoryOld;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        MemberJ memberJ = memberJRepositoryOld.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(memberJ.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(memberJ, delivery, orderItem);

        //주문 저장
        orderRepository.save(order); //CascadeType.ALL 옵션으로 order가 persist될 때 orderItem, delivery도 함께 persist

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    /**
     * 주문 조회
     */
    public List<Order> finsOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
