package com.cls.sitenavi.service;
import java.util.Map;

import com.cls.sitenavi.entity.User;

public interface ILoginService {
//    List<User> getVagueUserInfo(User user);
    User getDirectUserInfo(User user);
    User confirmUserInfo(User user);
    User getMail(User user);
    void saveToken(User user,String token);
    void deleteToken(User user,String token);
    Map<String,Object> searchPage (Map maps);

}
