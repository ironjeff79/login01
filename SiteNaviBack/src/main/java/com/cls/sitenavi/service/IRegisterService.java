package com.cls.sitenavi.service;
import com.cls.sitenavi.entity.User;

import java.util.List;
import java.util.Map;
public interface IRegisterService {
    List<Map<String, Object>> getUserId(String userId);
    List<Map<String, Object>> getMail(String mail);
    void insertUserInfo(User user);
}

