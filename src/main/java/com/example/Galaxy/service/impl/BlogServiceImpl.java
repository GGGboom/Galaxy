package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.BlogMapper;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.service.BlogService;
import com.example.Galaxy.util.Pager;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public String getAll(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Pager<Blog> pageInfo = new Pager(blogMapper.selectAll());
        return pageInfo.toString();
    }

    @Override
    public String getByUserId(Integer userId){
        return blogMapper.selectByUserId(userId).toString();
    }
}
