package com.example.Galaxy;


import com.example.Galaxy.dao.BlogMapper;
import com.example.Galaxy.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GalaxyApplicationTests {

    @Autowired
    private BlogMapper blogMapper;

    @Test
    void contextLoads() {
        Blog blog = new Blog();
        blog.setBlogId(1L);
        blogMapper.addBlogOfLikeByBlogId(blog);
        System.out.println(blog.getTotalLikes());
    }
}
