package com.example.Galaxy.dao;

import com.example.Galaxy.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    int deleteByPrimaryKey(Long blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Long blogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);

    List<Blog> selectAll(String createTime, String totalViews, String totalLikes, String totalComments);

    List<Blog> selectByUserId(@Param("userId") Long userId,
                              @Param("createTime") String createTime,
                              @Param("totalViews") String totalViews,
                              @Param("totalLikes") String totalLikes,
                              @Param("totalComments") String totalComments);

    int addBlogOfLikeByBlogId(Long blogId);

    int addTotalViewsByBlogId(Long blogId);
}