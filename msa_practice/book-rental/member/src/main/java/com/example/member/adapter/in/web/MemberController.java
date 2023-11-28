package com.example.member.adapter.in.web;

import com.example.member.adapter.in.web.response.MemberResponse;
import com.example.member.application.port.in.AddMemberUseCase;
import com.example.member.application.port.in.InquiryMemberUseCase;
import com.example.member.application.port.in.command.AddMemberCommand;
import com.example.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final AddMemberUseCase addMemberUseCase;
    private final InquiryMemberUseCase inquiryMemberUseCase;

    @PostMapping("/api/v1/member")
    public ResponseEntity<MemberResponse> addMember(@RequestBody AddMemberCommand command) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MemberResponse.toResponse(addMemberUseCase.addMember(command)));
    }

    @GetMapping("/api/v1/member/{no}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable long no) {
        Member member = inquiryMemberUseCase.getMember(no);
        return ResponseEntity.ok(MemberResponse.toResponse(member));
    }
}
