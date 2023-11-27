package me.seongim;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.seongim.jpabook.entity.Hello;
import me.seongim.jpabook.entity.QHello;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class JpashopApplicationTests {

    @PersistenceContext
    EntityManager em;

    @Test
    void contextLoads() {
        Hello hello = new Hello();
        em.persist(hello);
        JPAQueryFactory query = new JPAQueryFactory(em); QHello qHello = QHello.hello; //Querydsl Q타입 동작 확인
        Hello result = query
                .selectFrom(qHello)
                .fetchOne();
        Assertions.assertThat(result).isEqualTo(hello);
//lombok 동작 확인 (hello.getId())
        Assertions.assertThat(result.getId()).isEqualTo(hello.getId());
    }

}
