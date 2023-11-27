package me.seongim.jpabook.service;

import me.seongim.jpabook.domain.MemberJ;
import me.seongim.jpabook.repository.MemberJRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional //test클래스에서 test가 끝나면 rollback
public class MemberJServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberJRepositoryOld memberJRepositoryOld;

    @Test
    //@Rollback(false)
    public void 회원가입() throws Exception {
        //given
        MemberJ memberJ = new MemberJ();
        memberJ.setName("seongim");

        //when
        Long saveId = memberService.join(memberJ);

        //then
        assertEquals(memberJ, memberJRepositoryOld.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        MemberJ memberJ1 = new MemberJ();
        memberJ1.setName("seongim");

        MemberJ memberJ2 = new MemberJ();
        memberJ2.setName("seongim");

        //when
        memberService.join(memberJ1);
        memberService.join(memberJ2); //예외가 발생해야 한다.
        /*
        //expected = IllegalStateException.class로 대체 가
        try {
            memberService.join(member2); //예외가 발생해야 한다.
        } catch (IllegalStateException e) {
            return;
        }
         */

        //then
        fail("예외가 발생해야 한다.");
    }



}