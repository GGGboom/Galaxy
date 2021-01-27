package com.example.Galaxy.dao;

import com.example.Galaxy.entity.BlogOfLike;

public interface BlogOfLikeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogOfLike record);

    int insertSelective(BlogOfLike record);

    BlogOfLike selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogOfLike record);

    int updateByPrimaryKey(BlogOfLike record);

    BlogOfLike selectBlogOfLikeByBlogIdAndUserIdOfLike(Long blogId, Long userIdOfLike);

    int deleteBlogOfLikeByBlogId(Long blogId);
}