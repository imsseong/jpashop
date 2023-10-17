package me.seongim.jpabook.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.repository.order.OrderSimpleQueryDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    //재사용성이 부족함
    //API 스팩애 맞춘 코드가 리포지토리에 들어가는 단점
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                        "select new me.seongim.jpabook.repository.order.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }
}
