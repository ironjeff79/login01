//package com.cls.sitenavi.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.cls.sitenavi.entity.Comment;
//import com.cls.sitenavi.entity.User;
//import com.cls.sitenavi.mapper.CommentMapper;
//import com.cls.sitenavi.service.ICommentService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
//
//
//    @Override
//    public List<Comment> findAllByForeignId(Integer foreignId) {
//        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Comment::getForeignId,foreignId);
//        return this.baseMapper.selectList(wrapper);
//    }
//
//    @Override
//    public String getUsername(Integer id) {
//        String username = this.baseMapper.getUsernameByUserId(id);
//        return username;
//    }
//
//    @Override
//    public String getTargetname(Integer id) {
//        String targetName = this.baseMapper.getTargetnameByUserId(id);
//        return targetName;
//    }
//
//    @Override
//    public boolean deleteComment(Integer commentId) {
//        int affectedRows = this.baseMapper.deleteById(commentId);
//        return affectedRows > 0;
//    }
//
//}
