package com.example.login01.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Message<T> {
    @TableField(value = "code")
    private String code;
    @TableField(value = "message")
    private String msg;
    public String getCode() {
        return code;
    }
}
