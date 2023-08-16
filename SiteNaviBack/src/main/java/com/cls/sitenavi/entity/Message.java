package com.cls.sitenavi.entity;
import lombok.Data;
@Data
public class Message<T> {
    private String code;
    private String msg;
    public String getCode() {
        return code;
    }
}
