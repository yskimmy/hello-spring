package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public class MemberService {

    // test 에서 사용할 때 새로 인스턴스를 생성하면 서로 다른 객체에 대해 test 하는 것이 되므로 아래와 같이 처리해줘야 함
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /** 회원가입 **/
    public Long join(Member member) {
        // 같은 이름을 가진 중복회원 x
        validateDuplicateMember(member);    // 중복회원 검증

        memberRepository.save(member);
        return member.getId();

        /*  아래 내용을 조금 더 간결하게 표현한 것이 validateDuplicateMember() 메소드
        Optional<Member> result = memberRepository.findByName(member.getName());

        // Member member1 = result.get();       직접 꺼내기 그러나 비추

        result.ifPresent(m -> {   // Optional 로 감싸져 있어서 사용 가능
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
       */
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /** 전체회원 조회 **/
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /** id로 회원 찾기 **/
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
