package com.example.Galaxy.service;

import com.example.Galaxy.entity.Comments;

import java.util.List;

public interface CommentService {
    List<Comments> getAll(Long parentId, Long blogId);

    int insertSelective(Comments comments);

    int updateSelective(Comments comments);

    Long getUnread(Long userId);
}
