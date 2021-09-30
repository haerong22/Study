package com.example.basic;

import com.example.basic.member.Grade;
import com.example.basic.member.Member;
import com.example.basic.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        MemberServiceImpl memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member.getName() = " + member.getName());
        System.out.println("findMember.getName() = " + findMember.getName());
    }
}
