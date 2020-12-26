package com.example.Galaxy.service;

import com.example.Galaxy.entity.Blog;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BlogService {
    PageInfo<Blog> selectAll(Integer pageNum, Integer pageSize);

    PageInfo<Blog> selectBlogByUserId(Integer userId,Integer pageNum, Integer pageSize);

    int insertSelective(Blog blog);

    int updateBlogSelective(Blog blog);

    Blog selectBlogByBlogId(Long blogId);
}
