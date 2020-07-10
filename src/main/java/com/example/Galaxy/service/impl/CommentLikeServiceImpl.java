package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.CommentLikeMapper;
import com.example.Galaxy.dao.CommentsMapper;
import com.example.Galaxy.entity.CommentLike;
import com.example.Galaxy.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {
    @Autowired
    private CommentLikeMapper commentLikeMapper;

    @Override
    public int insertSelective(CommentLike commentLike) {
        return commentLikeMapper.insertSelective(commentLike);
    }

    @Override
    public CommentLike getCommentLike(CommentLike commentLike) {
        return commentLikeMapper.selectByCommentIdAndCreateByAndLikeUserId(commentLike);
    }

    @Override
    public int updateSelective(CommentLike commentLike) {
        return commentLikeMapper.updateSelective(commentLike);
    }
}
