package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.CommentsMapper;
import com.example.Galaxy.entity.Comments;
import com.example.Galaxy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public Long getUnread(Long userId) {
        return commentsMapper.getUnread(userId);
    }

    @Override
    public int updateSelective(Comments comments) {
        return commentsMapper.updateSelective(comments);
    }

    @Override
    public int insertSelective(Comments comments) {
        return commentsMapper.insertSelective(comments);
    }

    @Override
    @Cacheable(cacheNames = "Comment", key = "'getAll'")
    public List<Comments> getAll(Long parentId, Long blogId) {
        return commentsMapper.getAllByParent(parentId, blogId);
    }
}
