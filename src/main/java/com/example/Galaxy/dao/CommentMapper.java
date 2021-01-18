package com.example.Galaxy.dao;

import com.example.Galaxy.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Long commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectAllByParentIdAndBlogId(@Param("parentId")Long parentId, @Param("blogId")Long blogId);

    Long selectUnreadSumByUserId(Long userId);

    Long selectCommentSumByBlogId(Long blogId);

    int deleteByBlogId(Long blogId);

    List<Comment> selectByBlogId(Long blogId);
}