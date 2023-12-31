package com.cls.sitenavi.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cls.sitenavi.entity.Comment;
import com.cls.sitenavi.service.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
        public Map<String,Object> getCommentPage(Map maps){
            String sqll ="select * from comment";
            List<Comment> comments = jdbcTemplate.query(sqll, new Object[]{}, new BeanPropertyRowMapper<Comment>(Comment.class));
            Integer pageTotal = 0;
            Integer pageSize = (Integer)maps.get("pageSize");
            if (comments.size() % pageSize != 0) {
                pageTotal = comments.size() / pageSize + 1;
            }else {
                pageTotal = comments.size() / pageSize;}
            Object a = ((Integer)maps.get("page") - 1) * pageSize;
        String sql =  "select comment.* ,user.userId as user_name from comment left join user on comment.user_id  = user.id  where comment_id >= (select comment_id from comment order by comment_id limit " + a +", 1) limit " + pageSize +"";
        List<Comment> comment = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<Comment>(Comment.class));
        Map<String,Object>res=new HashMap<>();
        res.put("totalPage",pageTotal);
        res.put("comments",comment);
            return res;
    }
    
    public void insertComment(Map maps){
        String sqll ="select id from user where userId = '" +  maps.get("userId") + "'";
        Integer id =jdbcTemplate.queryForObject(sqll,Integer.class);
    String sql =  "insert into comment (user_id,rate,content,foreign_id) values ('" + id + "'"
    + ",'" + maps.get("rate") + "'" + ",'" + maps.get("content") + "'" + ",'" + maps.get("foreignId") + "')";
    jdbcTemplate.execute(sql);
    }
    
    public  List<Comment> getComment(Map maps){
    	String sqll = "select * from comment where  foreign_id = '" + maps.get("foreignId") + "'";
    	List<Comment> comments = jdbcTemplate.query(sqll, new Object[]{}, new BeanPropertyRowMapper<Comment>(Comment.class));
    	return comments;
    }    
    public void deleteComment(int commentId){
        String sql = "delete from comment where comment_id = '"  + commentId +"'";
        jdbcTemplate.execute(sql);
    }
    
    public String getUsername(Integer id) {
    	String sql = "select userId from user where id = ?";
    	String username = jdbcTemplate.queryForObject(sql,new Object[] {id},String.class);
        return username;
    }
    
    public String getTargetname(Integer id) {
    	String sql = "select userId from user where id = ?";
    	String username = jdbcTemplate.queryForObject(sql,new Object[] {id},String.class);
        return username;
    }
    public void saveReply(Map maps){
        String sqll ="select id from user where userId = '" +  maps.get("userId") + "'";
        Integer id =jdbcTemplate.queryForObject(sqll,Integer.class);
    String sql =  "insert into comment (user_id,content,foreign_id,pid,target) values ('" + id + "'"
    + ",'"  + maps.get("content") + "'" + ",'" + maps.get("foreignId") + "'" + ",'" + maps.get("pid") + "'" + ",'" + maps.get("target") + "')";   
    jdbcTemplate.execute(sql);
    }
}

