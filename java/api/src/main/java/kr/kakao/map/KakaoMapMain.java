package kr.kakao.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KakaoMapMain {

    public static void main(String[] args) {
        final String apiKey = args[0];
        KakaoApi.setApiKey(apiKey);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("주소를 입력하세요: ");
            String address = reader.readLine();

            double[] coordinate = KakaoApi.getAddressCoordinate(address);

            if (coordinate != null) {
                System.out.println("주소: " + address);
                System.out.println("위도: " + coordinate[0]);
                System.out.println("경도: " + coordinate[1]);
            } else {
                System.out.println("주소를 찾을 수 없습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
