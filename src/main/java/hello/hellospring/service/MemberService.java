package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    // 이랬는데
    private final MemberRepository memberRepository = new MemoryMemberRepository();

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
