package com.example.springdatajpa.repository;

import com.example.springdatajpa.dto.MemberDto;
import com.example.springdatajpa.entity.Member;
import com.example.springdatajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    void testMember() {
        Member member = new Member("memberA");

        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getUsername(), member.getUsername());
        assertEquals(findMember, member); // findMember == member : 같은 트랜잭션 안에서는 영속성 컨텍스트 동일성 보장
    }

    @Test
    void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertEquals(findMember1, member1);
        assertEquals(findMember2, member2);

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertEquals(2, all.size());

        // 카운트 검증
        long count = memberRepository.count();
        assertEquals(2, count);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deleteCount = memberRepository.count();
        assertEquals(0, deleteCount);
    }

    @Test
    void findByUsernameAndAgeGreaterThen() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("aaa", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("aaa", 15);

        assertEquals("aaa", result.get(0).getUsername());
        assertEquals(20, result.get(0).getAge());
        assertEquals(1, result.size());
    }

    @Test
    public void testNamedQuery() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("bbb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsername("aaa");

        assertEquals(m1, result.get(0));
    }

    @Test
    public void testQuery() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("bbb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findMember("aaa", 10);

        assertEquals(m1, result.get(0));
    }

    @Test
    public void findUsernameList() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("bbb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> result = memberRepository.findUsernameList();

        for (String s : result) {
            System.out.println("s = " + s);
        }

        assertEquals(2, result.size());
    }

    @Test
    public void findMemberDto() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("aaa", 10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> result = memberRepository.findMemberDto();

        assertEquals("aaa", result.get(0).getUsername());
        assertEquals("teamA", result.get(0).getTeamName());
    }

    @Test
    public void findByNames() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("bbb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("aaa", "bbb"));

        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void returnType() {
        Member m1 = new Member("aaa", 10);
        Member m2 = new Member("bbb", 20);
        Member m3 = new Member("ddd", 20);
        Member m4 = new Member("ddd", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);

        List<Member> memberList = memberRepository.findListByUsername("aaa");
        Member member = memberRepository.findMemberByUsername("aaa");
        Optional<Member> optionalMember = memberRepository.findOptionalByUsername("ccc");

        assertEquals(1, memberList.size());
        assertEquals(m1, member);
        assertTrue(optionalMember.isEmpty());

        List<Member> resultList = memberRepository.findListByUsername("ccc");
        Member result = memberRepository.findMemberByUsername("ccc");

        assertEquals(0, resultList.size()); // 조회 데이터가 없을 경우 빈 리스트 리턴
        assertNull(result); // 조회 데이터가 없을 경우 exception 발생 X -> spring data jpa 가 예외처리 하여 null 리턴

        assertThrows(IncorrectResultSizeDataAccessException.class,
                () -> memberRepository.findOptionalByUsername("ddd")); // 반환 타입은 단건 인데 여러 건이 조회 되었을 경우 예외 발생
    }

    @Test
    void paging() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        // when
        Page<Member> page = memberRepository.findPageByAge(age, pageRequest);
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

        // then
        List<Member> members = page.getContent();

        assertEquals(3, members.size());
        assertEquals(5, page.getTotalElements());
        assertEquals(0, page.getNumber());
        assertEquals(2, page.getTotalPages());
        assertTrue(page.isFirst());
        assertTrue(page.hasNext());

        Slice<Member> slice = memberRepository.findSliceByAge(age, pageRequest);
        List<Member> content = page.getContent();

        assertEquals(3, content.size());
        assertEquals(0, slice.getNumber());
        assertTrue(slice.isFirst());
        assertTrue(slice.hasNext());
    }
}

