package com.example.bookstorespringbootapi.utility;

import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class RandomDataGenerator {
    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    private final String FIRSTNAME = "$.results[*].name.first";
    private final String LASTNAME = "$.results[*].name.last";
    private final String EMAIL = "$.results[*].email";
    private final String USERNAME = "$.results[*].login.username";
    private final String REGISTERED = "$.results[*].registered.date";
    private final String PICTURE = "$.results[*].picture.large";

    private String getRandomUserData() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://randomuser.me/api/?inc=name,email,registered,login,picture&noinfo&nat=fr,us,gb,de,no,nl"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public ApplicationUser generateUser() throws IOException, InterruptedException {
        String data = getRandomUserData();
        ApplicationUser user = new ApplicationUser();
        user.setUserName(extractFromJSONArray(JsonPath.read(data, USERNAME)));
        user.setFirstName(extractFromJSONArray(JsonPath.read(data, FIRSTNAME)));
        user.setLastName(extractFromJSONArray(JsonPath.read(data, LASTNAME)));
        user.setEmail(extractFromJSONArray(JsonPath.read(data, EMAIL)).replace("example", "gmail"));
//        user.setImage(extractFromJSONArray(JsonPath.read(data, PICTURE)));
//        user.setRegisteredOn(formatDate(extractFromJSONArray(JsonPath.read(data, REGISTERED))));
        user.setRoles("ROLE_USER");
        user.setPassword("1");
//        user.setCredits(100);

        return user;
    }

    public Book generateBook() throws IOException, InterruptedException {
        return BookstoreFaker.bookFake();
    }

    public String generateImage() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://picsum.photos/200/300"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.headers().firstValue("location").get();
    }

    private static String extractFromJSONArray(JSONArray jsonArray){
        return (String) jsonArray.get(0);
    }

    private static LocalDateTime formatDate(String date){
        return LocalDateTime.parse(date.replace("Z", ""));
    }
}
