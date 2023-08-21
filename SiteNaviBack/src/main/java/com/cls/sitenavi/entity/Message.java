package com.cls.sitenavi.entity;
import lombok.Data;

import java.util.List;

@Data
public class Message<T> {
    private String code;
    private String msg;
    private User user;
    private List<User> userList;
    public String getCode() {
        return code;
    }
}
