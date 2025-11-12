package org.example.splearn.adapter.webapi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.splearn.adapter.webapi.dto.MemberRegisterResponse;
import org.example.splearn.application.member.provided.MemberRegister;
import org.example.splearn.domain.member.Member;
import org.example.splearn.domain.member.MemberRegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    @PostMapping("/api/members")
    public MemberRegisterResponse register(
            @RequestBody @Valid MemberRegisterRequest request
    ) {
        Member member = memberRegister.register(request);
        return MemberRegisterResponse.of(member);
    }
}
