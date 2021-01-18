package com.example.Galaxy.dao;

import com.example.Galaxy.entity.ExceptionLog;

public interface ExceptionLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(ExceptionLog record);

    int insertSelective(ExceptionLog record);

    ExceptionLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(ExceptionLog record);

    int updateByPrimaryKeyWithBLOBs(ExceptionLog record);

    int updateByPrimaryKey(ExceptionLog record);
}