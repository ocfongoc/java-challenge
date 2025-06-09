package jp.co.axa.apidemo.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Authentication response model")
public class AuthResponse {

    @ApiModelProperty(value = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZSIsImlhdCI6MTYxNjE1MjQwMCwiZXhwIjoxNjE2MjM4ODAwfQ.8c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c9c")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
} 