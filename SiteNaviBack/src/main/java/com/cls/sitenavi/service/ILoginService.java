package com.cls.sitenavi.service;
import java.util.List;
import java.util.Map;

public interface ILoginService {

    List<Map<String, Object>>  getUserInfo(String userId,String password);
}
