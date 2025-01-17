package com.knab.config;

import java.io.FileInputStream;
import java.util.Properties;


public class APIConfig {
    private static Properties properties;
    private static String apiKey;
    private static String apiToken;
    private static String baseUrl;

    static {
        try {
            properties = new Properties();
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties.load(file);
            
            apiKey = properties.getProperty("trello.api.key");
            apiToken = properties.getProperty("trello.api.token");
            baseUrl = properties.getProperty("trello.api.baseUrl");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getApiToken() {
        return apiToken;
    }
    
    public static String getBaseUrl() {
        return baseUrl;
    }
}