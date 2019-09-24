package com.gojek.assignment;

import com.google.gson.JsonElement;

import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Objects;

public class RestService {

    private static final String UTF_8 = "UTF-8";

    public JsonElement doGetRequest(final String url) {
        JsonElement response = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            HttpResponse httpResponse = client.execute(request);
            HttpEntity resEntity = httpResponse.getEntity();
            if (Objects.nonNull(resEntity)) {
                String responseStr = IOUtils.toString(resEntity.getContent(), UTF_8);
                if (!StringUtils.isEmpty(responseStr)) {
                    JsonParser parser = new JsonParser();
                    response = parser.parse(responseStr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
