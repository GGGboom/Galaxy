package com.example.Galaxy.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Comments implements Serializable {
    private Long commentId;

    private Long blogId;

    private Long userId;

    private String userAvatar;

    private Long parentCommentId;

    private Date createTime;

    private Date updateTime;

    private Long commentLikeAccount;

    private Boolean isDeleted;

    private Boolean isRead;

    private String commentContent;

    private List<Comments> childrenList;

    public List<Comments> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Comments> childrenList) {
        this.childrenList = childrenList;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar == null ? null : userAvatar.trim();
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCommentLikeAccount() {
        return commentLikeAccount;
    }

    public void setCommentLikeAccount(Long commentLikeAccount) {
        this.commentLikeAccount = commentLikeAccount;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }
}