package com.example.Galaxy.dao;

import com.example.Galaxy.entity.CommentLike;

import java.util.List;

public interface CommentLikeMapper {
    int insert(CommentLike record);

    int insertSelective(CommentLike record);

    int updateByPrimaryKeySelective(CommentLike commentLike);

    CommentLike selectByCommentIdAndCreateByAndLikeUserId(CommentLike commentLike);

    List<CommentLike> selectByCommentId(Long commentId);

    int deleteByCommentId(Long commentId);
}