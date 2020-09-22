package com.example.Galaxy.service.impl;

import com.example.Galaxy.dao.RecordMapper;
import com.example.Galaxy.entity.Record;
import com.example.Galaxy.service.RecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;

    @Override
    public PageInfo<Record> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Record> pageInfo = new PageInfo(recordMapper.selectAll());
        return pageInfo;
    }
}
