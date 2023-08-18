package com.cls.sitenavi.service;
import com.cls.sitenavi.entity.User;

public interface ILoginService {

    User getUserInfo(User user);
    User getMail(User user);
}
