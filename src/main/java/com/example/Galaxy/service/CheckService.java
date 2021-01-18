package com.example.Galaxy.service;


import com.example.Galaxy.entity.Comment;
import com.example.Galaxy.entity.CommentOfLike;
import com.example.Galaxy.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    public boolean checkComment(Long commentId) {
        List<CommentOfLike> commentLikes = commentService.selectCommentLikeByCommentId(commentId);
        return commentLikes.size() == 0;
    }

    public boolean checkBlog(Long blogId) {
        List<Comment> comments = commentService.selectByBlogId(blogId);
        return comments.size() == 0;
    }

    public boolean checkAccount(String account) {
        SysUser sysUser = userService.selectByAccount(account);
        return sysUser == null;
    }
}
