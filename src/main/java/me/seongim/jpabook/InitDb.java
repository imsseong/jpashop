package me.seongim.jpabook;

import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.domain.*;
import me.seongim.jpabook.domain.item.Book;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public MemberJ createMember(String name, String city, String streat, String zipcode) {
            MemberJ memberJ = new MemberJ();
            memberJ.setName(name);
            memberJ.setAddress(new Address(city, streat, zipcode));
            return memberJ;
        }

        public Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        public Delivery createDelivery(Address address) {
            Delivery delivery = new Delivery();
            delivery.setAddress(address);
            return delivery;
        }
        public void dbInit1() {
            MemberJ memberJ = createMember("userA", "서울", "1", "1111");
            em.persist(memberJ);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(memberJ.getAddress());
            Order order = Order.createOrder(memberJ, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            MemberJ memberJ = createMember("userB", "부산", "2", "2222");
            em.persist(memberJ);

            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING1 BOOK", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(memberJ.getAddress());
            Order order = Order.createOrder(memberJ, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }

}
