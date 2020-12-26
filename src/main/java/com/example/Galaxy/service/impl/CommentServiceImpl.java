package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.CommentsMapper;
import com.example.Galaxy.entity.Comments;
import com.example.Galaxy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long selectUnread(Long userId) {
        return commentsMapper.getUnread(userId);
    }

    @Override
    public int updateSelective(Comments comments) {
        return commentsMapper.updateSelective(comments);
    }

    @Override
    public int insertSelective(Comments comments) {
        return commentsMapper.insertSelective(comments);
    }

    @Override
    public List<Comments> selectAll(Long parentId, Long blogId) {
        List<Comments>list = (List<Comments>) redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selectAll");
        if(list==null){
            list = commentsMapper.getAllByParent(parentId,blogId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selectAll",list);
        }
        return list;
    }
}
