package com.example.Galaxy.service;

import com.example.Galaxy.entity.CommentLike;
import com.example.Galaxy.entity.Comments;

import java.util.List;

public interface CommentService {
    List<Comments> selectAll(Long parentId, Long blogId);

    int insertSelective(Comments comments);

    int updateSelective(Comments comments);

    Long selectUnread(Long userId);

    Long selectCommentSumByBlogId(Long blogId);



    int updateSelective(CommentLike commentLike);

    int insertSelective(CommentLike commentLike);

    CommentLike getCommentLike(CommentLike commentLike);
}
