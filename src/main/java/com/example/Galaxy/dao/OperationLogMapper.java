package com.example.Galaxy.dao;

import com.example.Galaxy.entity.OperationLog;

public interface OperationLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(OperationLog record);

    int insertSelective(OperationLog record);

    OperationLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(OperationLog record);

    int updateByPrimaryKeyWithBLOBs(OperationLog record);

    int updateByPrimaryKey(OperationLog record);
}