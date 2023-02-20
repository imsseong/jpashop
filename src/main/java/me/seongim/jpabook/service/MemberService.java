package me.seongim.jpabook.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.domain.Member;
import me.seongim.jpabook.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 전용, 성능 좋아짐
@RequiredArgsConstructor //final있는 필드만 가지고 생성자 만들어
public class MemberService {

    private final MemberRepository memberRepository;

    /*
    //@Autowired //생성자 하나면 자동으로 injection -> @RequiredArgsConstructor가 대신 해줌
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
