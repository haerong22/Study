package hello.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    @Test
    void memberTest() {
        Member member = new Member("idA", "memberA");
        memberRepository.initTable();
        memberRepository.save(member);

        Member findMember = memberRepository.find(member.getMemberId());

        assertThat(findMember.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }
}