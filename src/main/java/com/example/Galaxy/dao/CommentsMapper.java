package com.example.Galaxy.dao;

import com.example.Galaxy.entity.Comments;

public interface CommentsMapper {
    int insert(Comments record);

    int insertSelective(Comments record);
}