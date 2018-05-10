package com.divine.config;

import com.divine.exception.InvalidHttpCodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class HttpRequest {

    @Bean
    public ObjectMapper objectMapperInstance(){
        return new ObjectMapper();
    }

    private enum METHOD{
        GET,
        POST
    }

    /*
    * HTTP GEt request
    * */
    private String send(String url, METHOD method) {
        StringBuilder builder = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod(method.toString());
            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    builder = new StringBuilder();
                    while ((line = br.readLine()) != null){
                        builder.append(line);
                    }
                }
            } else {
                throw new InvalidHttpCodeException("Invalid response from server");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
