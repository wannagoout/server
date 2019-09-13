package com.wannago.user.dto;

public class UserToken {
    private String userToken;
    private Long id;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "userToken='" + userToken + '\'' +
                ", id=" + id +
                '}';
    }
}
