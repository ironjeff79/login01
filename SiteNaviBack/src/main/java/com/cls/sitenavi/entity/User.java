package com.cls.sitenavi.entity;
import lombok.Data;
@Data
public class User {
	private String id;
    private String userId;
    private String mail;
    private String password;
    private String phoneNum;
    private String state;
    private String province;
    private Integer offset;
    private Integer limit;
    private String token;

    public String getUserId() {
        return userId;
    }
    public String getId() {
        return id;
    }
}
