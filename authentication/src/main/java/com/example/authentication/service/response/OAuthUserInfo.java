package com.example.authentication.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthUserInfo {

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("picture")
    private String picture;

    public OAuthUserInfo() {
    }

    public String getSub() {
        return sub;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "OAuthUserInfo{" +
                "sub='" + sub + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
