package com.example.Galaxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.service.BlogService;
import com.example.Galaxy.util.Result;
import com.example.Galaxy.util.authorization.UserLoginToken;
import com.example.Galaxy.util.exception.CodeEnums;
import com.example.Galaxy.util.exception.GalaxyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectAll(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return blogService.getAll(pageNum, pageSize);
    }

    @UserLoginToken
    @ResponseBody
    @RequestMapping(value = "/mine", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Object selectBlogByUserId(HttpServletRequest httpServletRequest) throws RuntimeException {
        String userId = JWT.decode(httpServletRequest.getHeader("Authorization")).getAudience().get(0);
        return blogService.getByUserId(Integer.parseInt(userId));
    }


    /**
     * showdoc
     * @param userId      必选 Long    用户头像
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
    @UserLoginToken
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object addBlog(@RequestBody JSONObject params) {
        Long userId = params.getLong("userId");
        String userAvatar = params.getString("userAvatar");
        String blogTitle = params.getString("blogTitle");
        String blogContent = params.getString("blogContent");
        if (userId == null || userAvatar == null || blogTitle == null || blogContent == null) {
            throw new GalaxyException(CodeEnums.MISS_INFO.getCode(), CodeEnums.MISS_INFO.getMessage());
        }
        Blog blog = new Blog();
        blog.setUserId(userId);
        blog.setUserAvatar(userAvatar);
        blog.setBlogTitle(blogTitle);
        blog.setBlogContent(blogContent);
        blog.setBlogViews(0L);
        if (blogService.addBlog(blog) != 0) {
            return new Result(CodeEnums.SUCCESS.getCode(), CodeEnums.SUCCESS.getMessage());
        }
        return new Result(CodeEnums.EXCEPTION.getCode(), CodeEnums.EXCEPTION.getMessage());
    }

//    /**
//     * showdoc
//     * @param blogId      必选 Long    博客id
//     * @param userId      必选 Long    博客用户id
//     * @return {"code":0,message:"登录成功",data:{}}
//     * @catalog 博客
//     * @title
//     * @description 添加博客
//     * @method post
//     * @url /blog/add
//     */
//    @UserLoginToken
//    @ResponseBody
//    @RequestMapping(value = "/favorite", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
//    public Object addCollect(@RequestBody JSONObject params,HttpServletRequest httpServletRequest) throws RuntimeException{
//        String userId = JWT.decode(httpServletRequest.getHeader("Authorization")).getAudience().get(0);
//
//    }

}
