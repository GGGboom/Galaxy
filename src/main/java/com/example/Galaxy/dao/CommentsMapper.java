package com.example.Galaxy.dao;

import com.example.Galaxy.entity.Comments;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentsMapper {
    int insert(Comments record);

    int insertSelective(Comments record);

    List<Comments>selectAllByParentCommentIdAndBlogId(@Param("parentCommentId")Long parentCommentId,@Param("blogId")Long blogId);

    int updateByPrimaryKeySelective(Comments comments);

    Long selectUnreadSumByUserId(Long userId);

    Long selectCommentSumByBlogId(Long blogId);

    int deleteByBlogId(Long blogId);

    List<Comments> selectByBlogId(Long blogId);
}