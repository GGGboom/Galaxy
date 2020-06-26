package com.example.Galaxy.dao;

import com.example.Galaxy.entity.BlogLike;

public interface BlogLikeMapper {
    int insert(BlogLike record);

    int insertSelective(BlogLike record);
}