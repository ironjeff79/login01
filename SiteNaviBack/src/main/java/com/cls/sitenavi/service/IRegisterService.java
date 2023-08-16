package com.cls.sitenavi.service;
import java.util.List;
import java.util.Map;
public interface IRegisterService {
    List<Map<String, Object>> getUserId(String userId);
    List<Map<String, Object>> getMail(String mail);
    void updateUserInfo(String userId,String mail,String phoneNum,String password);
}

