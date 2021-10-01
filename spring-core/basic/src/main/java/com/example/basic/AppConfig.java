package com.example.basic;

import com.example.basic.discount.DiscountPolicy;
import com.example.basic.discount.FixDiscountPolicy;
import com.example.basic.member.MemberRepository;
import com.example.basic.member.MemberService;
import com.example.basic.member.MemberServiceImpl;
import com.example.basic.member.MemoryMemberRepository;
import com.example.basic.order.OrderService;
import com.example.basic.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
}
