package com.example.Galaxy.service;

import com.example.Galaxy.entity.Blog;

import java.util.List;

public interface BlogService {
    String getAll(int pageNum,int pageSize);

    String getByUserId(Integer userId);

    int addBlog(Blog blog);
}
