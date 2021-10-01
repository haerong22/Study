package com.example.basic.order;

import com.example.basic.discount.DiscountPolicy;
import com.example.basic.discount.FixDiscountPolicy;
import com.example.basic.member.Member;
import com.example.basic.member.MemberRepository;
import com.example.basic.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
