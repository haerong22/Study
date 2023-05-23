package openweather;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherEx {

    public static void main(String[] args) {
        final String apiKey = args[0];
        final String city = "Seoul";
        final String unit = "metric";

        final StringBuilder sb = new StringBuilder();
        sb.append("https://api.openweathermap.org/data/2.5/weather");
        sb.append("?q=").append(city);
        sb.append("&appid=").append(apiKey);
        sb.append("&unit=").append(unit);

        try {
            final URL url = new URL(sb.toString());
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            final int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                final StringBuilder content = new StringBuilder();

                while ((inputLine=in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                System.out.println("content = " + content);
                JsonObject weatherData = JsonParser.parseString(content.toString()).getAsJsonObject();
                JsonObject mainData = weatherData.getAsJsonObject("main");
                double temp = mainData.get("temp").getAsDouble();
                System.out.println("temp = " + temp);

                connection.disconnect();
            } else {
                System.out.println("responseCode = " + responseCode);
                System.out.println("responseMessage = " + connection.getResponseMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
