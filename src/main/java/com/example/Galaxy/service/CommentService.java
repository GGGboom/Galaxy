package com.example.Galaxy.service;

import com.example.Galaxy.entity.Comments;

import java.util.List;

public interface CommentService {
    List<Comments> selectAll(Long parentId, Long blogId);

    int insertSelective(Comments comments);

    int updateSelective(Comments comments);

    Long selectUnread(Long userId);

    Long selectCommentSumByBlogId(Long blogId);
}
