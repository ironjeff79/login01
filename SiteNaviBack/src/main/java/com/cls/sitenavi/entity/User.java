package com.cls.sitenavi.entity;
import lombok.Data;
@Data
public class User {
    private String userId;
    private String mail;
    private String password;
    private String phoneNum;
    public String getUserId() {
        return userId;
    }
}
