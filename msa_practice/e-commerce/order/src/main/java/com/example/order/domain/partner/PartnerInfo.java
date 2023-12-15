package com.example.order.domain.partner;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PartnerInfo {

    private final Long id;
    private final String partnerToken;
    private final String partnerName;
    private final String businessNo;
    private final String email;
    private final Partner.Status status;

    @Builder(access = AccessLevel.PRIVATE)
    private PartnerInfo(Long id, String partnerToken, String partnerName, String businessNo, String email, Partner.Status status) {
        this.id = id;
        this.partnerToken = partnerToken;
        this.partnerName = partnerName;
        this.businessNo = businessNo;
        this.email = email;
        this.status = status;
    }

    public static PartnerInfo of(Partner partner) {
        return PartnerInfo.builder()
                .id(partner.getId())
                .partnerToken(partner.getPartnerToken())
                .partnerName(partner.getPartnerName())
                .businessNo(partner.getBusinessNo())
                .email(partner.getEmail())
                .status(partner.getStatus())
                .build();
    }
}
