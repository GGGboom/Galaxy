package com.example.Galaxy.controller;

import com.auth0.jwt.JWT;
import com.example.Galaxy.service.BlogService;
import com.example.Galaxy.util.authorization.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @ResponseBody
    @RequestMapping(value = "/all",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public Object selectAll(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        return blogService.getAll(pageNum,pageSize);
    }

    @UserLoginToken
    @ResponseBody
    @RequestMapping(value = "/mine",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public Object selectBlogByUserId( HttpServletRequest httpServletRequest) throws RuntimeException{
        String userId = JWT.decode(httpServletRequest.getHeader("Authorization")).getAudience().get(0);
        return blogService.getByUserId(Integer.parseInt(userId));
    }
}
