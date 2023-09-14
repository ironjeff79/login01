package com.cls.sitenavi.service;
import java.util.Map;

import com.cls.sitenavi.entity.User;

public interface IWebService {
	Map<String,Object>getWebInfo(int a);
    User confirmUserInfo(User user);
 

}
