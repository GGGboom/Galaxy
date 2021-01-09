package com.example.Galaxy.service.impl;


import com.example.Galaxy.entity.CommentLike;
import com.example.Galaxy.entity.Comments;
import com.example.Galaxy.entity.User;
import com.example.Galaxy.service.CheckService;
import com.example.Galaxy.service.CommentService;
import com.example.Galaxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Override
    public boolean checkComment(Long commentId) {
        List<CommentLike> commentLikes = commentService.selectCommentsLikeByCommentId(commentId);
        return commentLikes.size() == 0;
    }

    @Override
    public boolean checkBlog(Long blogId) {
        List<Comments> comments = commentService.selectByBlogId(blogId);
        return comments.size() == 0;
    }

    @Override
    public boolean checkAccount(String account) {
        User user = userService.selectByAccount(account);
        return user == null;
    }
}
