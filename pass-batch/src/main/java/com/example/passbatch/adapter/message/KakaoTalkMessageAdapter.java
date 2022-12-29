package com.example.passbatch.adapter.message;

import com.example.passbatch.config.KakaoTalkMessageConfig;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoTalkMessageAdapter {
    private final WebClient webClient;

    public KakaoTalkMessageAdapter(KakaoTalkMessageConfig config) {
        webClient = WebClient.builder()
                .baseUrl(config.getHost())
                .defaultHeaders(h -> {
                    h.setBearerAuth(config.getToken());
                    h.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                }).build();

    }

    public boolean sendKakaoTalkMessage(final String uuid, final String text) {
        KakaoTalkMessageResponse response = webClient.post().uri("/v1/api/talk/friends/message/default/send")
                .body(BodyInserters.fromValue(new KakaoTalkMessageRequest(uuid, text)))
                .retrieve()
                .bodyToMono(KakaoTalkMessageResponse.class)
                .block();

        if (response == null || response.getSuccessfulReceiverUuids() == null) {
            return false;

        }
        return response.getSuccessfulReceiverUuids().size() > 0;

    }

}