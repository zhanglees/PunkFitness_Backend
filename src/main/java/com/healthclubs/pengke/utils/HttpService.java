package com.healthclubs.pengke.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class HttpService {

    private static HttpService currentService = new HttpService();

    private HttpService(){}

    public static HttpService getCurrentService() {
        return GetInstance();
    }

    private static HttpService GetInstance()
    {
        if (currentService == null)
        {
            currentService = new HttpService();
        }
        return currentService;
    }

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    public <T, TR> T PostUrl(String url, TR request, Class<T> type) throws IOException {
        String jsonStr = objectMapper.writeValueAsString(request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<TR> httpEntity = new HttpEntity<TR>(request, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        T result = objectMapper.readValue(responseEntity.getBody(), type);
        return result;
    }

    public <T> T GetUrl(String url, Class<T> type) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String jstoken = restTemplate.getForObject(url, String.class);
       // String jstoken1= "{errcode:40163,errmsg:ddd}";
//{"session_key":"2u\/dpE\/xEMR02Uua8E7c5Q==","openid":"oP1TP5cTTzZCgTXg-ZWG2d7sjfEA"}

        T result = objectMapper.readValue(jstoken, type);
        return result;
    }

}
