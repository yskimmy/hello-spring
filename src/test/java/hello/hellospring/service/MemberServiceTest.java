package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// 테스트는 각 테스트 별로 순서에 구애받지 않아야 함 => 한 번의 테스트가 끝날 때마다 repository 저장 값이 clear 되는 과정이 필요함!
class MemberServiceTest {

    // repository 저장 값 clear 를 위한 repository 객체 생성
    // but!!!! Service 쪽에서 만들어진 repository 인스턴스와 test 페이지에서 만든 인스턴스가 다르기 때문에 문제가 발생할 수 있음
    // DI!!!!

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("yeonsu");

        // when
        Long saveid = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveid).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외 () {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // memberService.join 메소드가 실행될 때 IllegalStateException이 터져야 된다 이런 뜻
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

 /*       try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.121212");
        }*/

        // then

    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}