package com.example.Galaxy.dao;

import com.example.Galaxy.entity.Tag;

import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(Long tagId);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Long tagId);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    int deleteByBlogId(Long blogId);

    List<Tag> selectTagByBlogId(Long blogId);
}