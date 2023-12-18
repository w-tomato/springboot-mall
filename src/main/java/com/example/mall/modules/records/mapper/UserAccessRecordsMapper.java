package com.example.mall.modules.records.mapper;

import com.example.mall.modules.records.entity.UserAccessRecords;

public interface UserAccessRecordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAccessRecords record);

    int insertSelective(UserAccessRecords record);

    UserAccessRecords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAccessRecords record);

    int updateByPrimaryKeyWithBLOBs(UserAccessRecords record);

    int updateByPrimaryKey(UserAccessRecords record);
}