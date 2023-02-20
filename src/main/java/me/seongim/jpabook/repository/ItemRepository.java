package me.seongim.jpabook.repository;

import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { //jpa에 저장하기 전까지 id값이 없음 -> 신규 등록
            em.persist(item);
        } else {
            em.merge(item); //update와 비슷
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
