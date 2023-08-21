package com.cls.sitenavi.service;
import com.cls.sitenavi.entity.User;

import java.util.List;

public interface ILoginService {

    List<User> getAllUserInfo();
    List<User> getUserInfo(User user);
    User confirmUserInfo(User user);
    User getMail(User user);
}
