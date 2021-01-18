package com.example.Galaxy.service;

import com.example.Galaxy.dao.CommentMapper;
import com.example.Galaxy.dao.CommentOfLikeMapper;
import com.example.Galaxy.entity.Comment;
import com.example.Galaxy.entity.CommentOfLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentOfLikeMapper commentOfLikeMapper;

    
    public List<Comment> selectByBlogId(Long blogId) {
        return commentMapper.selectByBlogId(blogId);
    }

    
    public List<CommentOfLike> selectCommentLikeByCommentId(Long commentId) {
        return commentOfLikeMapper.selectByCommentId(commentId);
    }

    
    public int deleteCommentLikeByCommentId(Long commentId) {
        return commentOfLikeMapper.deleteByCommentId(commentId);
    }

    
    public int deleteByBlogId(Long blogId) {
        return commentMapper.deleteByBlogId(blogId);
    }

    
    public Long selectUnread(Long userId) {
        return commentMapper.selectUnreadSumByUserId(userId);
    }

    
    @Cacheable(value = "selectCommentSumByBlogId", key = "#blogId")
    public Long selectCommentSumByBlogId(Long blogId) {
        return commentMapper.selectCommentSumByBlogId(blogId);
    }


    
    @Cacheable(value = "CommentCacheSelectAll", key = "#blogId")
    public List<Comment> selectAll(Long parentId, Long blogId) {
        return commentMapper.selectAllByParentIdAndBlogId(parentId, blogId);
    }

    
    @Caching(evict = {
            @CacheEvict(value = "selectCommentSumByBlogId", allEntries = true),
            @CacheEvict(value = "CommentCacheSelectAll", allEntries = true),
    })
    public int updateSelective(Comment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }


    /**
     * 创建评论，同时使用新的返回值的替换缓存中的值
     * 创建评论后会将selectCommentSumByBlogId、CommentCacheSelectAll缓存全部清空
     */
    @Caching(evict = {
            @CacheEvict(value = "selectCommentSumByBlogId", allEntries = true),
            @CacheEvict(value = "CommentCacheSelectAll", allEntries = true),
    })
    public int insertSelective(Comment comment) {
        return commentMapper.insertSelective(comment);
    }



    //-------------------------------------------------------------------------------


    
    public int insertSelective(CommentOfLike commentOfLike) {
        return commentOfLikeMapper.insertSelective(commentOfLike);
    }

    
    public CommentOfLike getCommentLike(CommentOfLike commentOfLike) {
        return commentOfLikeMapper.selectByCommentIdAndCreateByAndLikeUserId(commentOfLike);
    }

    
    public int updateSelective(CommentOfLike commentOfLike) {
        return commentOfLikeMapper.updateByPrimaryKeySelective(commentOfLike);
    }
}
