package com.quiz;

import com.google.gson.*;
import java.net.*;
import java.net.http.*;
import java.util.*;

public class ApiService {

    public static Map<String, String> getCountries() {
        Map<String, String> map = new HashMap<>();

        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://restcountries.com/v3.1/all?fields=name,capital"))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            

            System.out.println("API RESPONSE:");//changes!!
            System.out.println(response.body());

            Gson gson = new Gson();
            
            JsonElement element = JsonParser.parseString(response.body());

            if (!element.isJsonArray()) {
                System.out.println("API ERROR RESPONSE:");
                System.out.println(element);
                return map; // return empty safely
            }

            JsonArray array = element.getAsJsonArray();

            for (JsonElement countryElement : array) {
                JsonObject obj = countryElement.getAsJsonObject();

                String country = obj.getAsJsonObject("name")
                        .get("common").getAsString();

                // some countries have no capital → skip safely
                if (!obj.has("capital") || obj.get("capital").isJsonNull()) continue;

                JsonArray capArray = obj.getAsJsonArray("capital");
                if (capArray == null || capArray.size() == 0) continue;

                String capital = capArray.get(0).getAsString();

                if (capital == null || capital.isEmpty()) continue;

                map.put(country, capital);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}