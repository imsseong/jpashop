package me.seongim.jpabook.service;

import me.seongim.jpabook.domain.Address;
import me.seongim.jpabook.domain.MemberJ;
import me.seongim.jpabook.domain.Order;
import me.seongim.jpabook.domain.OrderStatus;
import me.seongim.jpabook.domain.item.Book;
import me.seongim.jpabook.domain.item.Item;
import me.seongim.jpabook.exception.NotEnoughStockException;
import me.seongim.jpabook.repository.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Fail.fail;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        MemberJ memberJ = createMember();

        Book book = createBook("jpa book", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(memberJ.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        MemberJ memberJ = createMember();
        Item item = createBook("jpa book", 10000, 10);

        int orderCount = 1;;

        //when
        orderService.order(memberJ.getId(), item.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다. ");
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        MemberJ memberJ = createMember();
        Item item = createBook("jpa book", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(memberJ.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
    }


    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        return book;
    }

    private MemberJ createMember() {
        MemberJ memberJ = new MemberJ();
        memberJ.setName("seongim");
        memberJ.setAddress(new Address("seoul", "street", "12345"));
        em.persist(memberJ);
        return memberJ;
    }

}