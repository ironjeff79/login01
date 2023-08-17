package com.cls.sitenavi.service;
import com.cls.sitenavi.entity.User;

import java.util.List;
import java.util.Map;

public interface IChangeService {
    List<Map<String, Object>>  changeUserInfo(String userId,String newUserId,String password);
    void changePass(String userId,String password);
    void changeUserInfo(User user);

}

