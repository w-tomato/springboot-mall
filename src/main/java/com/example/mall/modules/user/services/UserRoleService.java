package com.example.mall.modules.user.services;

import com.example.mall.modules.user.entity.UserRole;
import com.example.mall.modules.user.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/10
 */
@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    public List<UserRole> getUserRoleList(List<Integer> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Collections.emptyList();
        }
        return userRoleMapper.getUserRoleList(roleIdList);
    }
}
