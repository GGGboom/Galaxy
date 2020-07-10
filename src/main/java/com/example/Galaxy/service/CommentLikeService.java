package com.example.Galaxy.service;

import com.example.Galaxy.entity.CommentLike;

public interface CommentLikeService {
    int updateSelective(CommentLike commentLike);

    int insertSelective(CommentLike commentLike);

    CommentLike getCommentLike(CommentLike commentLike);
}
