//package com.cls.sitenavi.controllers;
//
//import com.alibaba.fastjson.JSON;
//import com.cls.sitenavi.service.ICommentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cls.sitenavi.entity.Message;
//import com.cls.sitenavi.entity.Comment;
//import com.cls.sitenavi.entity.User;
//import com.cls.sitenavi.service.ICommentService;
//
//
// import org.springframework.web.bind.annotation.*;
// import org.springframework.stereotype.Controller;
//
// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Objects;
// import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/comment")
//public class CommentController {
//
//    @Autowired
//    private ICommentService commentService;
//
////    @GetMapping("/all")
////    public Result<List<Comment>> getAll(){
////        List<Comment> list = commentService.list();
////        for(Comment ls : list){
////            ls.setCurrentUsername(commentService.getUsername(ls.getUserId()));
////            ls.setTargetName(commentService.getTargetname(ls.getTarget()));
////        }
////        return Result.success(list,"查询成功");
////    }
//
//    @PostMapping("/posted")
//    public Message<?> save(@RequestBody Comment comment){
//        Message msg = new Message();
//        try {
//            commentService.save(comment);
//            msg.setCode("success");
//            msg.setMsg("コメントを投稿しました。");
//            return msg;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            msg.setCode("warning");
//            msg.setMsg("コメントを投稿に失敗しました。 しばらくしてから再度お試しください。");
//            return msg;
//        }
//    }
//
////    @GetMapping("/info")
////    public Result<?> list(@RequestParam Integer foreignId){
////        try {
////            // 获取指定foreignId对应的所有评论
////            List<Comment> comments = commentService.findAllByForeignId(foreignId);
////
////            Map<String,Object> map = new HashMap<>();
////            BigDecimal rate = BigDecimal.ZERO;
////            map.put("rate",BigDecimal.ZERO);
////
////            // 筛选出具有评分的评论并计算平均评分
////            List<Comment> commentList = comments.stream().filter(comment -> comment.getRate() != null).collect(Collectors.toList());
////            commentList.stream().map(Comment::getRate).reduce(BigDecimal::add).ifPresent(res->{
////                       map.put("rate",res.divide(BigDecimal.valueOf(commentList.size()),1, RoundingMode.HALF_UP));
////                    });
////
////            // 获取所有顶级评论（没有父评论）及其子评论
////            List<Comment> rootComments = comments.stream().filter(comment -> comment.getPid() == null).collect(Collectors.toList());
////            for (Comment rootComment : rootComments) {
////                // 设置当前顶级评论的用户名
////                rootComment.setCurrentUsername(commentService.getUsername(rootComment.getUserId()));
////                List<Comment> children = comments.stream().filter(comment -> Objects.equals(rootComment.getCommentId(), comment.getPid())).collect(Collectors.toList());
////                for (Comment childComment : children) {
////                    // 设置子评论的用户名和目标名字
////                    childComment.setCurrentUsername(commentService.getUsername(childComment.getUserId()));
////                    childComment.setTargetName(commentService.getTargetname(childComment.getTarget()));
////                }
////                // 设置子评论的用户名和目标名字
////                rootComment.setChildren(children);
////            }
////            // 将顶级评论列表和平均评分放入map中
////            map.put("comments",rootComments);
////            return Result.success(map);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return Result.fail("获取评论失败，请稍后重试");
////        }
////    }
//
//    @PostMapping("/delete/{commentId}")
//    public  Message<?> deleteComment(@PathVariable Integer commentId){
//        Message msg = new Message();
//        if (commentService.deleteComment(commentId)){
//            msg.setCode("success");
//            msg.setMsg("コメントが削除しました。");
//            return msg;
//        }else {
//            msg.setCode("error");
//            return msg;
//        }
//    }
//
//
//}
