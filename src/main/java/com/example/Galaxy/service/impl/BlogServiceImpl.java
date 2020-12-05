package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.BlogMapper;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.service.BlogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    @Cacheable(cacheNames = "BlogCache", key = "'getAllBlog'")
    public PageInfo<Blog> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo(blogMapper.selectAll());
        return pageInfo;
    }

    @Override
    @Cacheable(cacheNames = "BlogCache", key = "'selectByUserId'")
    public PageInfo<Blog> getByUserId(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo(blogMapper.selectByUserId(userId));
        return pageInfo;
    }

    @Override
    public int addBlog(Blog blog) {
        return blogMapper.insertSelective(blog);
    }

    @Override
    public int updateBlogSelective(Blog blog) {
        return blogMapper.updateSelective(blog);
    }

    @Override
    @Cacheable(cacheNames = "BlogCache", key = "'getBlogByBlogId'")
    public Blog getBlogByBlogId(Long blogId) {
        return blogMapper.getBlogByBlogId(blogId);
    }
}
