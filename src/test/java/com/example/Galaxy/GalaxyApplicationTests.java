package com.example.Galaxy;


import com.example.Galaxy.dao.BlogMapper;
import com.example.Galaxy.entity.Blog;
import com.example.Galaxy.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class GalaxyApplicationTests {

    @Autowired
    private BlogMapper blogMapper;


    @Test
    void contextLoads() {
        System.out.println(blogMapper.addTotalViewsByBlogId(1L));
        System.out.println(blogMapper.addBlogOfLikeByBlogId(1L));
    }
}
