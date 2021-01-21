package com.example.Galaxy.service;

import com.example.Galaxy.dao.BlogMapper;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.entity.Comment;
import com.example.Galaxy.entity.SysUser;
import com.example.Galaxy.util.GalaxyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CheckService checkService;

    @Autowired
    private CommentService commentService;


    @Caching(evict = {
            @CacheEvict(value = "BlogCacheSelectAll", allEntries = true),
            @CacheEvict(value = "BlogCacheSelectBlogByUserId", allEntries = true),
            @CacheEvict(value = "BlogCacheSelectBlogByBlogId", allEntries = true),
    })
    public int insertSelective(Blog blog) {
        return blogMapper.insertSelective(blog);
    }


    @Caching(evict = {
            @CacheEvict(value = "BlogCacheSelectAll", allEntries = true),
            @CacheEvict(value = "BlogCacheSelectBlogByUserId", allEntries = true),
            @CacheEvict(value = "BlogCacheSelectBlogByBlogId", allEntries = true),
    })
    public int updateBlogSelective(Blog blog) {
        int row = 0;
        if (!checkService.checkBlog(blog.getBlogId())) {
            List<Comment> commentList = commentService.selectByBlogId(blog.getBlogId());
            for (Comment comment : commentList) {
                if (!checkService.checkComment(comment.getCommentId())) {
                    row += commentService.deleteCommentLikeByCommentId(comment.getCommentId());
                }
            }
            row += commentService.deleteByBlogId(blog.getBlogId());
        }
        row += blogMapper.updateByPrimaryKeySelective(blog);
        return row;
    }


    @Cacheable(value = "BlogCacheSelectAll", key = "#pageNum + #pageSize")
    public PageInfo<Blog> selectAll(Integer pageNum, Integer pageSize) {
        List<Blog> blogs = blogMapper.selectAll();
        List<Map<String, Object>> blogList = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        for (Blog blog : blogs) {
            Map<String, Object> map = null;
            SysUser user = userService.selectByPrimaryKey(blog.getUserId());
            try {
                map = mapBlog(blog,user);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            blogList.add(map);
        }
        PageInfo<Blog> pageInfo = new PageInfo(blogList);
        return pageInfo;
    }


    @Cacheable(value = "BlogCacheSelectBlogByUserId", key = "#userId + #pageNum + #pageSize")
    public PageInfo<Blog> selectBlogByUserId(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.selectByUserId(userId);
        List<Map<String, Object>> blogList = new ArrayList<>();
        SysUser user = userService.selectByPrimaryKey(userId);
        for (Blog blog : blogs) {
            Map<String, Object> map = null;
            try {
                map = mapBlog(blog,user);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            blogList.add(map);
        }
        PageInfo<Blog> pageInfo = new PageInfo(blogList);
        return pageInfo;
    }


    @Cacheable(value = "BlogCacheSelectBlogByBlogId", key = "#blogId")
    public Blog selectBlogByBlogId(Long blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    /**
     * 将blog和user加入一个map
     * @param blog
     * @param user
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Map mapBlog(Blog blog, SysUser user) throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> map = GalaxyUtil.mapUtil(blog);
        map.put("author", user.getName());
        map.put("userId", user.getUserId());
        map.put("avatar", user.getAvatar());
        return map;
    }
}
