package com.example.Galaxy.dao;

import com.example.Galaxy.entity.Comments;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentsMapper {
    int insert(Comments record);

    int insertSelective(Comments record);

    List<Comments>getAllByParent(@Param("parentCommentId")Long parentCommentId,@Param("blogId")Long blogId);

    int updateSelective(Comments comments);

    Long getUnread(Long userId);

    Long selectCommentSumByBlogId(Long blogId);
}