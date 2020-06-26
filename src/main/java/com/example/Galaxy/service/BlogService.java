package com.example.Galaxy.service;

import com.example.Galaxy.entity.Blog;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BlogService {
    PageInfo<Blog> getAll(int pageNum, int pageSize);

    PageInfo<Blog> getByUserId(Integer userId,int pageNum, int pageSize);

    int addBlog(Blog blog);

    int updateBlogSelective(Blog blog);

    Blog getBlogByBlogId(Long blogId);
}
