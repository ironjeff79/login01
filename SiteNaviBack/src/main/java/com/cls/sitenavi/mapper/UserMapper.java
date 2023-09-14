package com.cls.sitenavi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cls.sitenavi.entity.Comment;
import com.cls.sitenavi.entity.User;

public interface UserMapper extends BaseMapper<Comment> {
	List<User> getUserInfoByPassword(String id);



}
