package com.example.Galaxy.dao;

import com.example.Galaxy.entity.CommentOfLike;

import java.util.List;

public interface CommentOfLikeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CommentOfLike record);

    int insertSelective(CommentOfLike record);

    CommentOfLike selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CommentOfLike record);

    int updateByPrimaryKey(CommentOfLike record);

    CommentOfLike selectByCommentIdAndCreateByAndLikeUserId(CommentOfLike commentLike);

    List<CommentOfLike> selectByCommentId(Long commentId);

    int deleteByCommentId(Long commentId);
}