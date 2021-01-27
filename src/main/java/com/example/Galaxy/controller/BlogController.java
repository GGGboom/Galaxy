package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.entity.BlogOfLike;
import com.example.Galaxy.entity.Tag;
import com.example.Galaxy.service.BlogService;
import com.example.Galaxy.service.TagService;
import com.example.Galaxy.service.UserService;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.JsonResult;
import com.example.Galaxy.util.annotation.LogAnnotation;
import com.example.Galaxy.util.enums.ExceptionEnums;
import com.example.Galaxy.exception.GalaxyException;
import com.example.Galaxy.util.enums.OperationType;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {
    private final static Logger logger = Logger.getLogger(BlogController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    /**
     * showdoc
     *
     * @param pageNum  必选 Integer    用户头像
     * @param pageSize 必选 Integer  用户头像
     * @return {"code":0,message:"登录成功",data:{}}
     * @catalog 博客
     * @title
     * @description 返回所有博客
     * @method get
     * @url /blog/all
     */
    @LogAnnotation(description = "查询所有博客", operationType = OperationType.SELECT)
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/getAllBlog", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                            @RequestParam(name = "createTime", defaultValue = "desc") String createTime,
                            @RequestParam(name = "totalViews", defaultValue = "desc") String totalViews,
                            @RequestParam(name = "totalLikes", defaultValue = "desc") String totalLikes,
                            @RequestParam(name = "totalComments", defaultValue = "desc") String totalComments) {
        PageInfo<Map> mapPageInfo = blogService.selectAll(pageNum, pageSize, createTime, totalViews, totalLikes, totalComments);
        logger.info(mapPageInfo);
        return new JsonResult(ExceptionEnums.SUCCESS.getCode(), ExceptionEnums.SUCCESS.getMessage(), mapPageInfo);
    }


    /**
     * showdoc
     *
     * @return {"code":0,message:"登录成功",data:{}}
     * @catalog 博客
     * @title
     * @description 返回我的所有博客，需要登录
     * @method get
     * @url /blog/mine
     */
    @LogAnnotation(description = "查询我的博客", operationType = OperationType.SELECT)
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/getMine", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectBlogByUserId(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     @RequestParam(name = "createTime", defaultValue = "desc") String createTime,
                                     @RequestParam(name = "totalViews", defaultValue = "desc") String totalViews,
                                     @RequestParam(name = "totalLikes", defaultValue = "desc") String totalLikes,
                                     @RequestParam(name = "totalComments", defaultValue = "desc") String totalComments,
                                     HttpServletRequest httpServletRequest) throws RuntimeException {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        return new JsonResult(ExceptionEnums.SUCCESS.getCode(), ExceptionEnums.SUCCESS.getMessage(), blogService.selectBlogByUserId(userId, pageNum, pageSize, createTime, totalViews, totalLikes, totalComments));
    }


    @LogAnnotation(description = "通过博客Id获取博客", operationType = OperationType.SELECT)
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/getBlog", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectBlogByBlogId(@RequestParam("blogId") Long blogId, HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        Map map = null;
        try {
            map = blogService.mapBlog(blogService.selectBlogByBlogId(blogId), userService.selectByPrimaryKey(userId));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        map.put("tagList", tagService.selectByBlogId(blogId));
        return new JsonResult(ExceptionEnums.SUCCESS.getCode(), ExceptionEnums.SUCCESS.getMessage(), map);
    }


    /**
     * showdoc
     *
     * @param userAvatar  必选 String  用户头像
     * @param blogTitle   必选 String  博文标题
     * @param blogContent 必选 String  博文内容
     * @param file        可选
     * @return {"code":0,message:"登录成功",data:{}}
     * @catalog 博客
     * @title
     * @description 添加博客
     * @method post
     * @url /blog/add
     */
    @LogAnnotation(description = "添加博客", operationType = OperationType.INSERT)
    @ResponseBody
    @RequiresRoles(value = {"editor", "admin"}, logical = Logical.OR)
    @RequestMapping(value = "/postBlog", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object postBlogHandler(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        String description = params.getString("description");
        String blogTitle = params.getString("blogTitle");
        String blogContent = params.getString("blogContent");
        String flag = params.getString("flag");
        if (description == null || blogTitle == null || blogContent == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        Blog blog = new Blog();
        blog.setUserId(userId);
        blog.setDescription(description);
        blog.setBlogTitle(blogTitle);
        blog.setBlogContent(blogContent);
        blog.setCreateTime(new Date());
        blog.setTotalViews(0L);
        blogService.insertSelective(blog);
        //如果发布博客的时候上传了标签，那么添加标签
        if (flag != null) {
            String tagName = params.getString("tagName");
            if (tagName == null || tagName.equals("")) {
                throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
            }
            Tag tag = new Tag();
            tag.setBlogId(blog.getBlogId());
            tag.setCreateTime(new Date());
            tag.setTagName(tagName);
            tagService.insertSelective(tag);
        }
        return JsonResult.SUCCESS();
    }

    @LogAnnotation(description = "更新博客", operationType = OperationType.UPDATE)
    @ResponseBody
    @RequiresRoles("admin")
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object updateBlog(@RequestBody JSONObject params) {
        Long blogId = params.getLong("blogId");
        String description = params.getString("description");
        String blogTitle = params.getString("blogTitle");
        String blogContent = params.getString("blogContent");
        if (blogTitle == null || blogContent == null || blogId == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        Blog blog = blogService.selectBlogByBlogId(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogContent(blogContent);
        if (description != null) {
            blog.setDescription(description);
        }
        blogService.updateBlogSelective(blog);
        return JsonResult.SUCCESS();
    }


    @LogAnnotation(description = "删除博客", operationType = OperationType.UPDATE)
    @ResponseBody
    @RequiresRoles("admin")
    @RequestMapping(value = "/deleteBlog/{blogId}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public Object deleteBlogHandler(@PathVariable("blogId") Long blogId) {
        if (blogId == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setIsDeleted(true);
        blogService.deleteBlogByBlogId(blog);
        return JsonResult.SUCCESS();
    }

    /**
     * showdoc
     *
     * @param blogId 必选 Long    博客id
     * @return {"code":0,message:"登录成功",data:{}}
     * @catalog 博客
     * @title
     * @description 增加阅读量
     * @method post
     * @url /blog/addViews
     */
    @LogAnnotation(description = "添加博客阅读量", operationType = OperationType.UPDATE)
    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/addViews/{blogId}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Object blogViewsIncrement(@PathVariable("blogId") Long blogId) {
        if (blogId == null)
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        return JsonResult.SUCCESS(blogService.addTotalViewsOfBlog(blogId));
    }


    /**
     * showdoc
     *
     * @param blogId          必选 Long    博客id
     * @param blogLikeAccount 必选Long     博客的点赞总数
     * @param blogUserId      必选 Long    博客的用户id
     * @return {"code":0,message:"登录成功",data:{}}
     * @catalog 博客
     * @title
     * @description 点赞
     * @method post
     * @url /blog/favorite
     */
    @LogAnnotation(description = "收藏博客", operationType = OperationType.UPDATE)
    @ResponseBody
    @RequiresUser
    @RequestMapping(value = "/addLike/{blogId}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Object addLikeHandler(@PathVariable("blogId") Long blogId, HttpServletRequest httpServletRequest) {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        if (blogId == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        Blog blog = blogService.selectBlogByBlogId(blogId);
        if (blog == null) {
            throw new GalaxyException(ExceptionEnums.NOT_EXIST_BLOG.getCode(), ExceptionEnums.NOT_EXIST_BLOG.getMessage());
        }
        blogService.addBlogOfLikeByBlogId(blogId);
        BlogOfLike blogOfLike = blogService.selectBlogOfLikeByBlogIdAndUserIdOfLike(blogId, userId);
        if (blogOfLike == null) {
            blogOfLike = new BlogOfLike();
            blogOfLike.setBlogId(blogId);
            blogOfLike.setUserId(blog.getUserId());
            blogOfLike.setUserIdOfLike(userId);
            blogOfLike.setCreateTime(new Date());
            blogOfLike.setUpdateTime(new Date());
        }
        blogService.insertBlogOfLikeSelective(blogOfLike);
        return JsonResult.SUCCESS();
    }
}
