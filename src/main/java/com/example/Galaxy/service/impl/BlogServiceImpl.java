package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.BlogMapper;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.service.BlogService;
import com.example.Galaxy.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserService userService;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "BlogCacheSelectAll",allEntries = true),
            @CacheEvict(value = "BlogCacheSelectBlogByUserId",allEntries = true),
            @CacheEvict(value = "BlogCacheSelectBlogByBlogId",allEntries = true),
    })
    public int insertSelective(Blog blog) {
        return blogMapper.insertSelective(blog);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "BlogCacheSelectAll",allEntries = true),
            @CacheEvict(value = "BlogCacheSelectBlogByUserId",allEntries = true),
            @CacheEvict(value = "BlogCacheSelectBlogByBlogId",allEntries = true),
    })
    public int updateBlogSelective(Blog blog) {
        return blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    @Cacheable(value = "BlogCacheSelectAll",key = "#pageNum + #pageSize")
    public PageInfo<Blog> selectAll(Integer pageNum, Integer pageSize) {
        List<Blog> blogs = blogMapper.selectAll();
        List<Map<String, Object>> blogsMap = new ArrayList<>();
        for (Blog blog : blogs) {
            Map<String, Object> map = new HashMap<>();
            map.put("blog", blog);
            map.put("author", userService.selectByPrimaryKey(blog.getUserId()).getName());
            blogsMap.add(map);
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo(blogsMap);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "BlogCacheSelectBlogByUserId",key = "#userId + #pageNum + #pageSize")
    public PageInfo<Blog> selectBlogByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        List<Blog> blogs = blogMapper.selectByUserId(userId);
        List<Map<String, Object>> blogsMap = new ArrayList<>();
        for (Blog blog : blogs) {
            Map<String, Object> map = new HashMap<>();
            map.put("blog", blog);
            map.put("author", userService.selectByPrimaryKey(blog.getUserId()).getName());
            blogsMap.add(map);
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo(blogsMap);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "BlogCacheSelectBlogByBlogId",key = "#blogId")
    public Blog selectBlogByBlogId(Long blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }
}
