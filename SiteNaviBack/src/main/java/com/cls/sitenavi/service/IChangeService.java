package com.cls.sitenavi.service;
import com.cls.sitenavi.entity.User;

public interface IChangeService {

    void changeInfo(User user);
    User getInfo(User user);

}

