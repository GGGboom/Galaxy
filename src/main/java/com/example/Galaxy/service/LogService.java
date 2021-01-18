package com.example.Galaxy.service;

import com.example.Galaxy.dao.ExceptionLogMapper;
import com.example.Galaxy.dao.OperationLogMapper;
import com.example.Galaxy.entity.ExceptionLog;
import com.example.Galaxy.entity.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    public void addOperationLog(OperationLog operationLog) {
        operationLogMapper.insertSelective(operationLog);
    }

    public void addExceptionLog(ExceptionLog exceptionLog) {
        exceptionLogMapper.insertSelective(exceptionLog);
    }
}
