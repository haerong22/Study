package com.example.member.adapter.in.web.response;

import com.example.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {

    private Long no;
    private String id;
    private String name;
    private String email;
    private long point;

    public static MemberResponse toResponse(Member member) {
        return MemberResponse.builder()
                .no(member.getMemberNo())
                .id(member.getIdName().getId())
                .name(member.getIdName().getName())
                .email(member.getEmail().getAddress())
                .point(member.getPoint().getPointValue())
                .build();
    }
}
