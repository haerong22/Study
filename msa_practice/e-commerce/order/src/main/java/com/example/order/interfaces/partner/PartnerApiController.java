package com.example.order.interfaces.partner;

import com.example.order.application.partner.PartnerFacade;
import com.example.order.common.response.CommonResponse;
import com.example.order.domain.partner.PartnerCommand;
import com.example.order.domain.partner.PartnerInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partners")
public class PartnerApiController {

    private final PartnerFacade partnerFacade;
    private final PartnerDtoMapper partnerDtoMapper;

    @PostMapping
    public CommonResponse<PartnerResponse.Register> registerPartner(
            @Valid @RequestBody PartnerRequest.Register request
    ) {
        PartnerCommand command = partnerDtoMapper.of(request);
        PartnerInfo partnerInfo = partnerFacade.registerPartner(command);
        return CommonResponse.success(PartnerResponse.Register.of(partnerInfo));
    }
}
