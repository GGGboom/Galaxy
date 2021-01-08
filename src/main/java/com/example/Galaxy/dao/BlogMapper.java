package com.example.Galaxy.dao;

import com.example.Galaxy.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface BlogMapper {
    int insert(Blog record);

    int insertSelective(Blog record);

    List<Blog> selectAll();

    List<Blog> selectByUserId(@Param("userId") Integer userId);

    int updateByPrimaryKeySelective(Blog blog);

    Blog selectByPrimaryKey(Long blogId);
}