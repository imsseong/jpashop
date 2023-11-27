package me.seongim.jpabook.repository;

import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.domain.MemberJ;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberJRepositoryOld {

    //@PersistenceContext -> spring boot jpa는 @Autowired로 대체 가능 -> @RequiredArgsConstructor
    private final EntityManager em;

    public void save(MemberJ memberJ) {
        em.persist(memberJ);
    }

    public MemberJ findOne(Long id) {
        return em.find(MemberJ.class, id);
    }

    public List<MemberJ> findAll() {
        return em.createQuery("select m from Member m", MemberJ.class)
                .getResultList();
    }

    public List<MemberJ> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", MemberJ.class)
                .setParameter("name", name)
                .getResultList();
    }
}
