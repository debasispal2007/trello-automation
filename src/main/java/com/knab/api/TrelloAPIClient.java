package com.knab.api;

import com.knab.config.APIConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class TrelloAPIClient {
    
    private final String baseURL;
    private final String apiKey;
    private final String apiToken;
    private String currentToken;

    public TrelloAPIClient() {
        this.apiKey = APIConfig.getApiKey();
        this.apiToken = APIConfig.getApiToken();
        this.baseURL = APIConfig.getBaseUrl();
        this.currentToken = this.apiToken; 
    }

    public Response createBoard(String boardName) {
        return given()
                .baseUri(baseURL)
                .queryParam("name", boardName)
                .queryParam("key", apiKey)
                .queryParam("token", currentToken)
                .contentType(ContentType.JSON)
                .when()
                .post("/boards")
                .then()
                .extract()
                .response();
    }

    public Response updateBoard(String boardId, String newName) {
        return given()
                .baseUri(baseURL)
                .queryParam("name", newName)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .contentType(ContentType.JSON)
                .when()
                .put("/boards/" + boardId)
                .then()
                .extract()
                .response();
    }

    public Response deleteBoard(String boardId) {
        return given()
                .baseUri(baseURL)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .when()
                .delete("/boards/" + boardId)
                .then()
                .extract()
                .response();
    }

    public Response createList(String boardId, String listName) {
        return given()
                .baseUri(baseURL)
                .queryParam("name", listName)
                .queryParam("idBoard", boardId)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .contentType(ContentType.JSON)
                .when()
                .post("/lists")
                .then()
                .extract()
                .response();
    }
    
    public Response verifyApiCredentials() {
        return given()
                .baseUri(baseURL)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .when()
                .get("/members/me")
                .then()
                .extract()
                .response();
    }
    
    public void setInvalidToken(String invalidToken) {
        this.currentToken = invalidToken;
    }
}