package com.example.mall.modules.useraction.mapper;

import com.example.mall.modules.useraction.entity.UserActionLog;

public interface UserActionLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(UserActionLog record);

    int insertSelective(UserActionLog record);

    UserActionLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(UserActionLog record);

    int updateByPrimaryKey(UserActionLog record);
}