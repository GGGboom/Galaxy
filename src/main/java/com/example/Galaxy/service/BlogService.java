package com.example.Galaxy.service;

import com.example.Galaxy.dao.BlogMapper;
import com.example.Galaxy.dao.BlogOfLikeMapper;
import com.example.Galaxy.dao.TagMapper;
import com.example.Galaxy.entity.*;
import com.example.Galaxy.util.GalaxyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
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
    private TagMapper tagMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogOfLikeMapper blogOfLikeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;



    public int insertSelective(Blog blog) {
        return blogMapper.insertSelective(blog);
    }

    public int updateBlogSelective(Blog blog) {
        return blogMapper.updateByPrimaryKeySelective(blog);
    }

    /**
     * 删除blog需要删除blog_file、blog_of_like、comment、comment_of_like、tag
     * @param blog
     * @return
     */
    public int deleteBlogByBlogId(Blog blog) {
        int row = 0;
        row += commentService.deleteCommentOfLikeByBlogId(blog.getBlogId());
        row += commentService.deleteByBlogId(blog.getBlogId());
        row += blogOfLikeMapper.deleteBlogOfLikeByBlogId(blog.getBlogId());
        row += tagMapper.deleteByBlogId(blog.getBlogId());
        row += blogMapper.updateByPrimaryKeySelective(blog);
        return row;
    }

    public int insertBlogOfLikeSelective(BlogOfLike blogOfLike) {
        return blogOfLikeMapper.insertSelective(blogOfLike);
    }

    public int addBlogOfLikeByBlogId(Long blogId) {
        return blogMapper.addBlogOfLikeByBlogId(blogId);
    }

    public int addBlogOfLike(BlogOfLike blogOfLike) {
        return blogOfLikeMapper.insertSelective(blogOfLike);
    }

    public int addTotalViewsOfBlog(Long BlogId) {
        return blogMapper.addTotalViewsByBlogId(BlogId);
    }

    public BlogOfLike selectBlogOfLikeByBlogIdAndUserIdOfLike(Long blogId, Long userIdOfLike) {
        return blogOfLikeMapper.selectBlogOfLikeByBlogIdAndUserIdOfLike(blogId, userIdOfLike);
    }

    public PageInfo<Map> selectAll(Integer pageNum, Integer pageSize, String createTime, String totalViews, String totalLikes, String totalComments) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.selectAll(createTime, totalViews, totalLikes, totalComments);
        List<Map<String, Object>> blogList = new ArrayList<>();
        for (Blog blog : blogs) {
            Map<String, Object> map = null;
            SysUser user = userService.selectByPrimaryKey(blog.getUserId());
            try {
                map = mapBlog(blog, user);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put("tagList", tagMapper.selectTagByBlogId(blog.getBlogId()));
            blogList.add(map);
        }
        PageInfo<Map> pageInfo = new PageInfo(blogList);
        return pageInfo;
    }

    public PageInfo<Map> selectBlogByUserId(Long userId, Integer pageNum, Integer pageSize, String createTime, String totalViews, String totalLikes, String totalComments) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.selectByUserId(userId, createTime, totalViews, totalLikes, totalComments);
        List<Map<String, Object>> blogList = new ArrayList<>();
        SysUser user = userService.selectByPrimaryKey(userId);
        for (Blog blog : blogs) {
            Map<String, Object> map = null;
            try {
                map = mapBlog(blog, user);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put("tagList", tagMapper.selectTagByBlogId(blog.getBlogId()));
            blogList.add(map);
        }
        PageInfo<Map> pageInfo = new PageInfo(blogList);
        return pageInfo;
    }

    public Blog selectBlogByBlogId(Long blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    /**
     * 将blog和user加入一个map
     *
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
