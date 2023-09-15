package com.cls.sitenavi.entity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Message<T> {
    private String code;
    private String msg;
    private User user;
    private List<Web> webList;
    private List<User> userList;
    private List<Comment> comments;
    private String page;
    private Map<String,Object> maps;
    private BigDecimal rate;
    private String sessionId;
    private String token;
    public String getCode() {
        return code;
    }
}
