package com.example.Galaxy.dao;

import com.example.Galaxy.entity.Record;

import java.util.List;

public interface RecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKeyWithBLOBs(Record record);

    int updateByPrimaryKey(Record record);

    List<Record> selectAll();

    Record selectByUserId(Long userId);
}