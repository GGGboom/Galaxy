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
    void contextLoads() throws InvocationTargetException, IllegalAccessException {
        Blog blog = blogMapper.selectByPrimaryKey(1L);
        mapBlog(blog, blog.getUserId());
    }

    private Map mapBlog(Blog blog, Long userId) throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<? extends Blog> clazz = blog.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                String fieldName = methodName.substring(methodName.indexOf("get") + 3);
                fieldName = fieldName.toLowerCase().charAt(0) + fieldName.substring(1);
                Object value = method.invoke(blog, (Object[]) null);
                map.put(fieldName, value);
            }
        }
        return map;
    }
}
