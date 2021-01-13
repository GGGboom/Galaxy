package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.OperationLogMapper;
import com.example.Galaxy.entity.OperationLog;
import com.example.Galaxy.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    private OperationLogMapper operationLogMapper;


    @Override
    public void addOperationLog(OperationLog operationLog) {
        operationLogMapper.insertSelective(operationLog);
    }
}
