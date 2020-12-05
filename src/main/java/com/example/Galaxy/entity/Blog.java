package com.example.Galaxy.entity;

import java.io.Serializable;
import java.util.Date;

public class Blog implements Serializable {
    private Long blogId;

    private Long userId;

    private String userAvatar;

    private String blogTitle;

    private Long blogViews;

    private Long blogCommentAccount;

    private Long blogLikeAccount;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    private String blogContent;

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

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle == null ? null : blogTitle.trim();
    }

    public Long getBlogViews() {
        return blogViews;
    }

    public void setBlogViews(Long blogViews) {
        this.blogViews = blogViews;
    }

    public Long getBlogCommentAccount() {
        return blogCommentAccount;
    }

    public void setBlogCommentAccount(Long blogCommentAccount) {
        this.blogCommentAccount = blogCommentAccount;
    }

    public Long getBlogLikeAccount() {
        return blogLikeAccount;
    }

    public void setBlogLikeAccount(Long blogLikeAccount) {
        this.blogLikeAccount = blogLikeAccount;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent == null ? null : blogContent.trim();
    }
}