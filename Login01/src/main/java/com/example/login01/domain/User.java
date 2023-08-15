package com.example.login01.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    @TableField(value = "userId")
    private String userId;
    @TableField(value = "mail")
    private String mail;
    @TableField(value = "password")
    private String password;
    @TableField(value = "phoneNum")
    private String phoneNum;

    public String getUserId() {
        return userId;
    }
}
