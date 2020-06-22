package com.example.Galaxy.controller;

import com.example.Galaxy.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @RequestMapping(value = "/mine",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public Object selectBlogByUserId(@RequestParam(name = "userId", required = false) Integer userId){
        return blogService.getByUserId(userId);
    }
}
