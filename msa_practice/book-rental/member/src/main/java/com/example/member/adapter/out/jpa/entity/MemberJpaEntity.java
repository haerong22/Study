package com.example.member.adapter.out.jpa.entity;

import com.example.member.domain.Member;
import com.example.member.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member")
public class MemberJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    private String id;
    private String name;
    private String presentPwd;
    private String pastPwd;
    private String emailAddress;
    private long point;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "member_authority", joinColumns = @JoinColumn(name = "member_no"))
    private List<UserRole> authorites;

    public static MemberJpaEntity fromMember(Member member) {
        return MemberJpaEntity.builder()
                .memberNo(member.getMemberNo())
                .id(member.getIdName().getId())
                .name(member.getIdName().getName())
                .presentPwd(member.getPassword().getPresent())
                .pastPwd(member.getPassword().getPast())
                .emailAddress(member.getEmail().getAddress())
                .point(member.getPoint().getPointValue())
                .authorites(
                        member.getAuthorites().stream()
                                .map(Authority::getRoleName)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public Member toMember() {
        return Member.builder()
                .memberNo(memberNo)
                .idName(IDName.create(id, name))
                .password(Password.create(presentPwd, pastPwd))
                .email(Email.create(emailAddress))
                .authorites(
                        authorites.stream()
                                .map(Authority::create)
                                .collect(Collectors.toList())
                )
                .point(Point.create(point))
                .build();
    }
}
