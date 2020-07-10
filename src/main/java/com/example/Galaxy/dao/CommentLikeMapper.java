package com.example.Galaxy.dao;

import com.example.Galaxy.entity.CommentLike;

public interface CommentLikeMapper {
    int insert(CommentLike record);

    int insertSelective(CommentLike record);

    int updateSelective(CommentLike commentLike);

    CommentLike selectByCommentIdAndCreateByAndLikeUserId(CommentLike commentLike);
}