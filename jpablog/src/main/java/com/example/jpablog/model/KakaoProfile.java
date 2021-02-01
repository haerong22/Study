package com.example.jpablog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class KakaoProfile {
    private Integer id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Data
    public static class KakaoAccount {
        private Boolean profile_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
    }

    @Data
    public static class Profile {
        private String nickname;
        private String thumbnail_image_url;
        private String profile_image_url;
    }
}




