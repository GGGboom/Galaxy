package com.example.Galaxy.service;

import com.example.Galaxy.dao.TagMapper;
import com.example.Galaxy.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    public List<Tag> selectByBlogId(Long blogId) {
        return tagMapper.selectTagByBlogId(blogId);
    }

    public int insertSelective(Tag tag) {
        return tagMapper.insertSelective(tag);
    }
}
