import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientModule {

    public static void main(String[] args) throws Exception {
        var client = HttpClient.newHttpClient();

        // GET
        var getRequest = HttpRequest.newBuilder(URI.create("https://postman-echo.com/get"))
                .GET()
                .build();

        // 동기 요청
        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        printlnWithTread(response.statusCode());
        printlnWithTread(response.body());

        // 비동기 요청
        client.sendAsync(getRequest, HttpResponse.BodyHandlers.ofString())
                        .thenAccept((resp) -> {
                            printlnWithTread(resp.statusCode());
                            printlnWithTread(resp.body());
                        });

        // POST
        var postRequest = HttpRequest.newBuilder(URI.create("https://postman-echo.com/post"))
                .POST(
                        HttpRequest.BodyPublishers.ofString("{\"num\": 1}")
                ).build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        printlnWithTread(postResponse.statusCode());
        printlnWithTread(postResponse.body());
    }

    private static void printlnWithTread(Object obj) {
        System.out.printf("%s %s\n", Thread.currentThread().getName(), obj);
    }
}
