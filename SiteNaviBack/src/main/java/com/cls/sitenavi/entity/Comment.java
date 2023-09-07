package com.cls.sitenavi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import lombok.Data;
//import org.springframework.data.annotation.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 网站评分
     */
    private BigDecimal rate;

    /**
     * 内容
     */
    private String content;

    /**
     * 业务模块的id，用于区分不同网站
     */
    private Integer foreignId;

    /**
     * 回复对象
     */
    private Integer target;

    /**
     * 	父级评论id
     */
    private Integer pid;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 子评论
     */
    @TableField(exist = false)
    private List<Comment> children;

    @TableField(exist = false)
    private String currentUsername;

    @TableField(exist = false)
    private String targetName;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getForeignId() {
        return foreignId;
    }

    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public String toString() {
        return "Comment{" +
            "commentId = " + commentId +
            ", userId = " + userId +
            ", rate = " + rate +
            ", content = " + content +
            ", foreignId = " + foreignId +
            ", target = " + target +
            ", pid = " + pid +
            ", createtime = " + createtime +
                ", userName = " + userName +
        "}";
    }
}
