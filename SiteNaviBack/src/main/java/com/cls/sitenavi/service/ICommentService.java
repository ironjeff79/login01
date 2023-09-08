package com.cls.sitenavi.service;
import java.util.List;
import java.util.Map;

import com.cls.sitenavi.entity.Comment;

public interface ICommentService {
	Map<String,Object> getCommentPage (Map maps);
	
	String getUsername(Integer id);
	
	 String getTargetname(Integer id);
    
    void insertComment (Map maps);
    
    List<Comment> getComment(Map maps);

    void deleteComment(int commentId);
}
