package me.seongim.jpabook.repository;

import me.seongim.jpabook.domain.MemberJ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberJRepository extends JpaRepository<MemberJ, Long> {

    //select m from Member m where m.name = ?
    List<MemberJ> findByName(String name);
}
