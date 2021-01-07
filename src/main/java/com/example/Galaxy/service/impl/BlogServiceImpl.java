package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.BlogMapper;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.service.BlogService;
import com.example.Galaxy.service.RedisCacheService;
import com.example.Galaxy.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisCacheService redisCacheService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int insertSelective(Blog blog) {
        redisCacheService.deleteCacheByClass(this.getClass());
        return blogMapper.insertSelective(blog);
    }

    @Override
    public int updateBlogSelective(Blog blog) {
        redisCacheService.deleteCacheByClass(this.getClass());
        return blogMapper.updateSelective(blog);
    }

    @Override
    public PageInfo<Blog> selectAll(Integer pageNum, Integer pageSize) {
        PageInfo<Blog> pageInfo = (PageInfo<Blog>) redisTemplate.opsForHash().get(this.getClass().getSimpleName(), "selectAll:" + pageNum + pageSize.toString());
        if (pageInfo == null) {
            List<Blog> blogs = blogMapper.selectAll();
            List<Map<String, Object>> blogsMap = new ArrayList<>();
            for (Blog blog : blogs) {
                Map<String, Object> map = new HashMap<>();
                map.put("blog", blog);
                map.put("author", userService.selectByUserId(blog.getUserId()).getName());
                blogsMap.add(map);
            }
            PageHelper.startPage(pageNum, pageSize);
            pageInfo = new PageInfo(blogsMap);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(), "selectAll:" + pageNum + pageSize, pageInfo);
        }
        return pageInfo;
    }

    @Override
    public PageInfo<Blog> selectBlogByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        PageInfo<Blog> pageInfo = (PageInfo<Blog>) redisTemplate.opsForHash().get(this.getClass().getSimpleName(), "selectBlogByUserId:" + userId + pageNum + pageSize.toString());
        if (pageInfo == null) {
            List<Blog> blogs = blogMapper.selectByUserId(userId);
            List<Map<String, Object>> blogsMap = new ArrayList<>();
            for (Blog blog : blogs) {
                Map<String, Object> map = new HashMap<>();
                map.put("blog", blog);
                map.put("author", userService.selectByUserId(blog.getUserId()).getName());
                blogsMap.add(map);
            }
            PageHelper.startPage(pageNum, pageSize);
            pageInfo = new PageInfo(blogsMap);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(), "selectBlogByUserId:" + userId + pageNum + pageSize, pageInfo);
        }
        return pageInfo;
    }

    @Override
    public Blog selectBlogByBlogId(Long blogId) {
        Blog blog = (Blog) redisTemplate.opsForHash().get(this.getClass().getSimpleName(), "selectBlogByBlogId:" + blogId);
        if (blog == null) {
            blog = blogMapper.getBlogByBlogId(blogId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName() + blogId, "selectBlogByBlogId:", blog);
        }
        return blog;
    }
}
