package com.example.Galaxy.service;

import com.example.Galaxy.entity.CommentLike;
import com.example.Galaxy.entity.Comments;

import java.util.List;

public interface CommentService {
    List<Comments> selectAll(Long parentId, Long blogId);

    int insertSelective(Comments comments);

    int updateSelective(Comments comments);

    int deleteByBlogId(Long blogId);

    Long selectUnread(Long userId);

    Long selectCommentSumByBlogId(Long blogId);

    List<Comments> selectByBlogId(Long blogId);

    List<CommentLike> selectCommentsLikeByCommentId(Long commentId);

    int deleteCommentsLikeByCommentId(Long commentId);

    int updateSelective(CommentLike commentLike);

    int insertSelective(CommentLike commentLike);

    CommentLike getCommentLike(CommentLike commentLike);
}
