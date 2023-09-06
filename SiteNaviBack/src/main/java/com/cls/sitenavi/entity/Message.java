package com.cls.sitenavi.entity;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Message<T> {
    private String code;
    private String msg;
    private User user;
    private List<User> userList;
    private List<Comment> comments;
    private String page;
    private Map<String,Object> maps;
    public String getCode() {
        return code;
    }
}
