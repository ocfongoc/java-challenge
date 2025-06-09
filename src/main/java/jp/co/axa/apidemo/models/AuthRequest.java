package jp.co.axa.apidemo.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Authentication request model")
public class AuthRequest {

    @ApiModelProperty(value = "Username for authentication", example = "john", required = true)
    private String username;

    @ApiModelProperty(value = "Password for authentication", example = "password123", required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
} 