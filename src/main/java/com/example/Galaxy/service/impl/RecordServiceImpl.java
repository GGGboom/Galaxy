package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.RecordMapper;
import com.example.Galaxy.entity.Record;
import com.example.Galaxy.service.RecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int insertSelective(Record record) {
        return recordMapper.insertSelective(record);
    }

    @Override
    public Record selectByUserId(Long userId) {
        Record record = (Record) redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selectByUserId");
        if (record==null){
            record = recordMapper.selectByUserId(userId);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selectByUserId",record);
        }
        return record;
    }

    @Override
    public int updateByPrimaryKeySelective(Record record) {
        return recordMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return recordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Record selectByPrimaryKey(Long id) {
        Record record = (Record) redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selectByPrimaryKey");
        if(record==null){
            record = recordMapper.selectByPrimaryKey(id);
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selectByPrimaryKey",record);
        }
        return record;
    }

    @Override
    public PageInfo<Record> selectAll(int pageNum, int pageSize) {
        PageInfo<Record> pageInfo = (PageInfo<Record>) redisTemplate.opsForHash().get(this.getClass().getSimpleName(),"selectAll");
        if (pageInfo==null){
            PageHelper.startPage(pageNum,pageSize);
            pageInfo = new PageInfo(recordMapper.selectAll());
            redisTemplate.opsForHash().put(this.getClass().getSimpleName(),"selectAll",pageInfo);
        }
        return pageInfo;
    }
}
