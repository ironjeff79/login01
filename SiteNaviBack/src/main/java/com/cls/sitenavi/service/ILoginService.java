package com.cls.sitenavi.service;
import com.cls.sitenavi.entity.User;

import java.util.List;

public interface ILoginService {

    List<User> getAllUserInfo();
    List<User> getVagueUserInfo(User user);
    User getDirectUserInfo(User user);
    User confirmUserInfo(User user);
    User getMail(User user);
}
