package org.example.subscriber.service.mqtt;


import java.io.IOException;
import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MQTTRepository {
    private final String BASE_URL = "http://localhost:18083/api/v5/";
    private final String USERNAME = "687d602a30c44db7";
    private final String PASSWORD = "9AUnIZ9AFn7wd9C9AxSb6rtFfYJ089BemSIF9CtFhBnm1RXDA";

    public HttpRequest getAuthorization(String endpoint) {
        return HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Authorization", "Basic " +
                        java.util.Base64.getEncoder().encodeToString(String.format("%s:%s", USERNAME, PASSWORD).getBytes())) // Замените на ваш API-ключ или логин:пароль
                .header("Content-Type", "application/json")
                .GET()
                .build();
    }

    public String getResponseBodyByEndPoint(String endPoint) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = getAuthorization(endPoint);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


}
