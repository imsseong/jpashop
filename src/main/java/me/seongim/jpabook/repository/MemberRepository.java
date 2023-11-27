package me.seongim.jpabook.repository;

import me.seongim.jpabook.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    //핵심 비지니스로직, 재사용성 있거나 엔티티 검색 혹은 특화된 경우 -> 기본은 MemberRepositoryCustom
    //공용성 없고 특정 api에 종속되어 있거나 등등 -> MemberQueryRepository 별도로 분리
    List<Member> findByUsername(String username);
}
