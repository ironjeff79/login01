package com.cls.sitenavi.service;
import java.util.List;
import java.util.Map;

import com.cls.sitenavi.entity.Comment;
import com.cls.sitenavi.entity.User;

public interface ILoginService {

//    List<User> getAllUserInfo();
//    List<User> getVagueUserInfo(User user);
    User getDirectUserInfo(User user);
    User confirmUserInfo(User user);
    User getMail(User user);
    Map<String,Object> searchPage (Map maps);

    List<Comment> getAllComment (User user);
}
