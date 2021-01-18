package com.example.Galaxy.dao;

import com.example.Galaxy.entity.Ledger;

public interface LedgerMapper {
    int deleteByPrimaryKey(Long ledgerId);

    int insert(Ledger record);

    int insertSelective(Ledger record);

    Ledger selectByPrimaryKey(Long ledgerId);

    int updateByPrimaryKeySelective(Ledger record);

    int updateByPrimaryKeyWithBLOBs(Ledger record);

    int updateByPrimaryKey(Ledger record);
}