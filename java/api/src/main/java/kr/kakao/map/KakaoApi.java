package kr.kakao.map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;

public class KakaoApi {

    private static String API_KEY = "";

    private static final String API_BASE_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final Gson gson = new Gson();

    public static double[] getAddressCoordinate(String address) throws IOException {
        String encodedAddress = URLEncoder.encode(address, "UTF-8");
        String requestUrl = API_BASE_URL + "?query=" + encodedAddress;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestUrl);
        httpGet.setHeader("Authorization", "KakaoAK " + API_KEY);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
            JsonArray documents = jsonObject.getAsJsonArray("documents");

            if (documents.size() > 0) {
                JsonObject document = documents.get(0).getAsJsonObject();
                double latitude = document.get("y").getAsDouble();
                double longitude = document.get("x").getAsDouble();
                return new double[]{latitude, longitude};
            } else {
                return null;
            }
        }
    }

    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }
}
