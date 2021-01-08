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

    @Override
    public PageInfo<Record> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Record> pageInfo = new PageInfo(recordMapper.selectAll());
        return pageInfo;
    }

    @Override
    public Record selectByUserId(Long userId) {
        return recordMapper.selectByUserId(userId);
    }

    @Override
    public int insertSelective(Record record) {
        return recordMapper.insertSelective(record);
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
        return recordMapper.selectByPrimaryKey(id);
    }
}
