package com.example.Galaxy.service;

import com.example.Galaxy.entity.Record;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RecordService {
    PageInfo<Record> selectAll(int pageNum, int pageSize);

    int insertSelective(Record record);

    int updateByPrimaryKeySelective(Record record);

    int deleteByPrimaryKey(Long id);

    Record selectByPrimaryKey(Long id);

    Record selectByUserId(Long userId);
}
