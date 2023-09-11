package com.cls.sitenavi.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.Comment;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.service.ICommentService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class CommentController {
	@Autowired
	public ICommentService commentService;

	//评论管理页面
	@PostMapping("/commentPage")
	public String getCommentPage(@RequestBody Map maps) {
		Map<String, Object> a = commentService.getCommentPage(maps);
		Message msg = new Message();
		msg.setCode("success");
		msg.setMaps(a);
		return JSON.toJSONString(msg);
	}

	//页面评论显示
	@PostMapping("/comment")
	public String getComment(@RequestBody Map maps) {
		List<Comment> comments = commentService.getComment(maps);

		// 获取所有顶级评论（没有父评论）及其子评论
		List<Comment> rootComments = comments.stream().filter(comment -> comment.getPid() == null)
				.collect(Collectors.toList());
		for (Comment rootComment : rootComments) {
			// 设置当前顶级评论的用户名
			rootComment.setCurrentUsername(commentService.getUsername(rootComment.getUserId()));
			List<Comment> children = comments.stream()
					.filter(comment -> Objects.equals(rootComment.getCommentId(), comment.getPid()))
					.collect(Collectors.toList());
			for (Comment childComment : children) {
				// 设置子评论的用户名和目标名字
				childComment.setCurrentUsername(commentService.getUsername(childComment.getUserId()));
				childComment.setTargetName(commentService.getTargetname(childComment.getTarget()));
			}
			// 设置子评论的用户名和目标名字
			rootComment.setChildren(children);
		}
		Message msg = new Message();
		BigDecimal rate = BigDecimal.ZERO;
		msg.setRate(BigDecimal.ZERO);
		// 筛选出具有评分的评论并计算平均评分
		List<Comment> commentList = comments.stream().filter(comment -> comment.getRate() != null)
				.collect(Collectors.toList());
		commentList.stream().map(Comment::getRate).reduce(BigDecimal::add).ifPresent(res -> {
			msg.setRate(res.divide(BigDecimal.valueOf(commentList.size()), 1, RoundingMode.HALF_UP));
		});
		msg.setCode("success");
		msg.setComments(rootComments);
		return JSON.toJSONString(msg);
	}

	@PostMapping("/commentPosted")
	public String commentPosted(@RequestBody Map maps) {
		commentService.insertComment(maps);
		Message msg = new Message();
		msg.setCode("success");
		return JSON.toJSONString(msg);
	}

	@PostMapping("/deleteComment")
	public String deleteComment(@RequestBody int commentId) {
		commentService.deleteComment(commentId);
		Message msg = new Message();
		msg.setCode("success");
		return JSON.toJSONString(msg);
	}

	@PostMapping("/saveReply")
	public String saveReply(@RequestBody Map maps) {
		commentService.saveReply(maps);
		Message msg = new Message();
		msg.setCode("success");
		return JSON.toJSONString(msg);
	}
}