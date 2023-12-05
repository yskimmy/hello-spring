package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

// 테스트는 각 테스트 별로 순서에 구애받지 않아야 함 => 한 번의 테스트가 끝날 때마다 repository 저장 값이 clear 되는 과정이 필요함!
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // test 1건 완료 시 마다 repository clear 해주는 메소드
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("yeonsu");

        repository.save(member);    // repository에 저장
        Member result = repository.findById(member.getId()).get();    // 저장되었는지 id 값 보내서 확인

        // Assertions.assertEquals(member, result);
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1  = new Member();
        member1.setName("yeonsu1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("yeonsu2");
        repository.save(member2);

        Member result = repository.findByName("yeonsu2").get();

        Assertions.assertThat(member2).isEqualTo(result);

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
