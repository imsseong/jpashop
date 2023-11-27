package me.seongim.jpabook.service;

import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.domain.MemberJ;
import me.seongim.jpabook.repository.MemberJRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 전용, 성능 좋아짐
@RequiredArgsConstructor //final있는 필드만 가지고 생성자 만들어
public class MemberService {

    private final MemberJRepository memberJRepository;

    /*
    //@Autowired //생성자 하나면 자동으로 injection -> @RequiredArgsConstructor가 대신 해줌
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(MemberJ memberJ) {
        validateDuplicateMember(memberJ); //중복 회원 검증
        memberJRepository.save(memberJ);
        return memberJ.getId();
    }

    private void validateDuplicateMember(MemberJ memberJ) {
        List<MemberJ> findMemberJS = memberJRepository.findByName(memberJ.getName());
        if (!findMemberJS.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<MemberJ> findMembers() {
        return memberJRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public MemberJ findOne(Long memberId) {
        return memberJRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        MemberJ memberJ = memberJRepository.findById(id).get();
        memberJ.setName(name);
    }
}
