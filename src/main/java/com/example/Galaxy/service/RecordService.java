package com.example.Galaxy.service;

import com.example.Galaxy.entity.Record;
import com.github.pagehelper.PageInfo;

public interface RecordService {
    PageInfo<Record> getAll(int pageNum, int pageSize);
}
