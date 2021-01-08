package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.CommentLikeMapper;
import com.example.Galaxy.dao.CommentsMapper;
import com.example.Galaxy.entity.CommentLike;
import com.example.Galaxy.entity.Comments;
import com.example.Galaxy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private CommentLikeMapper commentLikeMapper;

    @Override
    public Long selectUnread(Long userId) {
        return commentsMapper.selectUnreadSumByUserId(userId);
    }

    @Override
    @Cacheable(value = "selectCommentSumByBlogId", key = "#blogId")
    public Long selectCommentSumByBlogId(Long blogId) {
        return commentsMapper.selectCommentSumByBlogId(blogId);
    }

    @Override
    @Cacheable(value = "CommentCacheSelectAll", key = "#blogId")
    public List<Comments> selectAll(Long parentId, Long blogId) {
        return commentsMapper.selectAllByParentCommentIdAndBlogId(parentId, blogId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "selectCommentSumByBlogId", allEntries = true),
            @CacheEvict(value = "CommentCacheSelectAll", allEntries = true),
    })
    public int updateSelective(Comments comments) {
        return commentsMapper.updateByPrimaryKeySelective(comments);
    }


    /**
     * 创建评论，同时使用新的返回值的替换缓存中的值
     * 创建评论后会将selectCommentSumByBlogId、CommentCacheSelectAll缓存全部清空
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = "selectCommentSumByBlogId", allEntries = true),
            @CacheEvict(value = "CommentCacheSelectAll", allEntries = true),
    })
    public int insertSelective(Comments comments) {
        return commentsMapper.insertSelective(comments);
    }



    //-------------------------------------------------------------------------------


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
        return commentLikeMapper.updateByPrimaryKeySelective(commentLike);
    }
}
