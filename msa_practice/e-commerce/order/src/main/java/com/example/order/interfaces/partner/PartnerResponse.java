package com.example.order.interfaces.partner;

import com.example.order.domain.partner.Partner;
import com.example.order.domain.partner.PartnerInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class PartnerResponse {

    @Getter
    @ToString
    public static class Register {
        private final String partnerToken;
        private final String partnerName;
        private final String businessNo;
        private final String email;
        private final Partner.Status status;

        @Builder(access = AccessLevel.PRIVATE)
        private Register(String partnerToken, String partnerName, String businessNo, String email, Partner.Status status) {
            this.partnerToken = partnerToken;
            this.partnerName = partnerName;
            this.businessNo = businessNo;
            this.email = email;
            this.status = status;
        }

        public static Register of(PartnerInfo partnerInfo) {
            return Register.builder()
                    .partnerToken(partnerInfo.getPartnerToken())
                    .partnerName(partnerInfo.getPartnerName())
                    .businessNo(partnerInfo.getBusinessNo())
                    .email(partnerInfo.getEmail())
                    .status(partnerInfo.getStatus())
                    .build();
        }
    }
}
