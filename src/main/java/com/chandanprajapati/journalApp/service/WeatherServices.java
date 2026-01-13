package com.chandanprajapati.journalApp.service;

import com.chandanprajapati.journalApp.apiresponse.WeatherResponse;
import com.chandanprajapati.journalApp.appcache.cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class WeatherServices {
    @Value("${weather.api.key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private cache appcache;
    //private static final String  API="https://api.weatherstack.com/forecast?access_key=API_KEY&query=CITY";
    @Autowired
    private RedisServices redisServices;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisServices.get("weather of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        }else {
            String finalAPI = appcache.APP_CACHE.get("weather_api").replace("CITY", city).replace("API_KEY", apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisServices.set("weather_of_" + city, body, 300l);
            }
            return body;
        }

    }
}

