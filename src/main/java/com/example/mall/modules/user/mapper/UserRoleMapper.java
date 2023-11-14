package com.example.mall.modules.user.mapper;

import com.example.mall.modules.user.entity.UserRole;

import java.util.List;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/10
 */
public interface UserRoleMapper {

    List<UserRole> getUserRoleList(List<Integer> roleIdList);
}
