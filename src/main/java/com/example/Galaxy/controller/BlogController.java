package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.service.BlogService;
import com.example.Galaxy.util.JWTUtil;
import com.example.Galaxy.util.Result;
import com.example.Galaxy.util.annotation.LogAnnotation;
import com.example.Galaxy.util.enums.ExceptionEnums;
import com.example.Galaxy.exception.GalaxyException;
import com.example.Galaxy.util.enums.OperationType;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {
    private final static Logger logger = Logger.getLogger(BlogController.class);

    @Autowired
    private BlogService blogService;

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
    @LogAnnotation(description = "查询所有博客",operationType = OperationType.SELECT)
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectAll(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return new Result(ExceptionEnums.SUCCESS.getCode(), ExceptionEnums.SUCCESS.getMessage(), blogService.selectAll(pageNum, pageSize));
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
    @LogAnnotation(description = "查询我的博客",operationType = OperationType.SELECT)
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/mine", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectBlogByUserId(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     HttpServletRequest httpServletRequest) throws RuntimeException {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        return new Result(ExceptionEnums.SUCCESS.getCode(), ExceptionEnums.SUCCESS.getMessage(), blogService.selectBlogByUserId(userId.intValue(), pageNum, pageSize));
    }


    @LogAnnotation(description = "通过博客Id获取博客",operationType = OperationType.SELECT)
    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "/getBlog", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectBlogByBlogId(@RequestParam("blogId") Long blogId) {
        return new Result(ExceptionEnums.SUCCESS.getCode(), ExceptionEnums.SUCCESS.getMessage(), blogService.selectBlogByBlogId(blogId));
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
    @LogAnnotation(description = "添加博客",operationType = OperationType.INSERT)
    @ResponseBody
    @RequiresRoles("editor")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object addBlog(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) throws RuntimeException {
        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
        Long userId = JWTUtil.getUserId(token);
        String description = params.getString("description");
        String blogTitle = params.getString("blogTitle");
        String blogContent = params.getString("blogContent");
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
        if (blogService.insertSelective(blog) != 0) {
            return Result.SUCCESS();
        }
        return new Result(ExceptionEnums.EXCEPTION.getCode(), ExceptionEnums.EXCEPTION.getMessage());
    }

    @LogAnnotation(description = "更新博客",operationType = OperationType.UPDATE)
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
        return Result.SUCCESS();
    }


    @LogAnnotation(description = "删除博客",operationType = OperationType.UPDATE)
    @ResponseBody
    @RequiresRoles("admin")
    @RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object deleteBlog(@RequestParam(name = "blogId", required = true) Long blogId, HttpServletRequest httpServletRequest) {
//        String token = JWT.decode(httpServletRequest.getHeader("Authorization")).getToken();
//        Long userId = JWTUtil.getUserId(token);
        if (blogId == null) {
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        }
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setIsDeleted(true);
        blogService.updateBlogSelective(blog);
        return Result.SUCCESS();
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
    @LogAnnotation(description = "添加博客阅读量",operationType = OperationType.UPDATE)
    @ResponseBody
    @RequestMapping(value = "/addViews", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Object blogViewsIncrement(@RequestBody JSONObject params) {
        Long blogId = params.getLong("blogId");
        if (blogId == null)
            throw new GalaxyException(ExceptionEnums.MISS_INFO.getCode(), ExceptionEnums.MISS_INFO.getMessage());
        Blog blog = blogService.selectBlogByBlogId(params.getLong("blogId"));
        blog.setTotalViews(blog.getTotalViews() + 1);
        blogService.updateBlogSelective(blog);
        return Result.SUCCESS();
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
    @LogAnnotation(description = "收藏博客",operationType = OperationType.UPDATE)
    @ResponseBody
    @RequestMapping(value = "/favorite", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public Object addFavorite(@RequestBody JSONObject params, HttpServletRequest httpServletRequest) throws RuntimeException {
        Long blogId = params.getLong("blogId");
        Long blogLikeAccount = params.getLong("blogLikeAccount");
        Long blogUserId = params.getLong("blogUserId");
        Blog blog = new Blog();
        blog.setTotalLikes(blogLikeAccount + 1);
        blog.setBlogId(blogId);
        blogService.updateBlogSelective(blog);
        return Result.SUCCESS();
    }

}
