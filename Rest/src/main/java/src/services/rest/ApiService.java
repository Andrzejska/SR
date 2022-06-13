package src.services.rest;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ApiService {
    public Map<String, Object> getEthnicityInfoByFullName(String fullName) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        UriBuilder uri = UriBuilder.fromUri("https://api.diversitydata.io").queryParam("fullname", fullName);

        HttpRequest request = HttpRequest.newBuilder().uri(uri.build()).build();

        HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = result.statusCode();
        if (statusCode == 301)
            throw new Error("Unauthorized. Error occurred while processing get ethnicity info request.");
        else if (statusCode == 422)
            throw new Error("Unprocessable entity. Error occurred while processing get ethnicity info request");
        else if (statusCode != 200)
            throw new Error("Error occurred while processing get ethnicity info request" + result);


        return new ObjectMapper().readValue(result.body(), HashMap.class);
    }

    public Map<String, String> getFortuneForToday() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://yerkee.com/api/fortune");

        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = result.statusCode();
        if (statusCode == 301)
            throw new Error("Unauthorized. Error occurred while processing get fortune request.");
        else if (statusCode == 422)
            throw new Error("Unprocessable entity. Error occurred while processing get fortune request.");
        else if (statusCode != 200)
            throw new Error("Error occurred while processing get fortune request." + result);

        return new ObjectMapper().readValue(result.body(), HashMap.class);

    }
}
