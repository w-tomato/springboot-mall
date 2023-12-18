package com.example.mall.modules.records.services;

import com.example.mall.modules.records.entity.UserAccessRecords;
import com.example.mall.modules.records.mapper.UserAccessRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author w-tomato
 * @description
 * @date 2023/12/7
 */
@Service
public class UserAccessRecordsService {

    @Autowired
    private UserAccessRecordsMapper userAccessRecordsMapper;

    public void insert(UserAccessRecords userAccessRecords) {
        userAccessRecordsMapper.insert(userAccessRecords);
    }

}
