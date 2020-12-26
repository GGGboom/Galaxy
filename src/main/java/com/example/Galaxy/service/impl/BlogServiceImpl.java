package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.BlogMapper;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.service.BlogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<Blog> selectAll(Integer pageNum, Integer pageSize) {
        PageInfo<Blog>pageInfo = (PageInfo<Blog>) redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selectAll");
        if (pageInfo==null){
            PageHelper.startPage(pageNum, pageSize);
            pageInfo = new PageInfo(blogMapper.selectAll());
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selectAll",pageInfo);
        }
        return pageInfo;
    }

    @Override
    public PageInfo<Blog> selectBlogByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        PageInfo<Blog>pageInfo = (PageInfo<Blog>) redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selectBlogByUserId");
        if (pageInfo==null){
            PageHelper.startPage(pageNum, pageSize);
            pageInfo = new PageInfo(blogMapper.selectByUserId(userId));
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selectBlogByUserId",pageInfo);
        }
        return pageInfo;
    }

    @Override
    public int insertSelective(Blog blog) {
        return blogMapper.insertSelective(blog);
    }

    @Override
    public int updateBlogSelective(Blog blog) {
        return blogMapper.updateSelective(blog);
    }

    @Override
    @Cacheable(cacheNames = "BlogCache", key = "#blogId")
    public Blog selectBlogByBlogId(Long blogId) {
        Blog blog = (Blog) redisTemplate.opsForHash().get(this.getClass().getSimpleName(), "selectBlogByBlogId");
        if (blog == null) {
            blog = blogMapper.getBlogByBlogId(blogId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(), "selectBlogByBlogId", blog);
        }
        return blog;
    }
}
