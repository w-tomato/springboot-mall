package com.example.mall.modules.order.services;

import com.example.mall.modules.order.entity.Order;
import com.example.mall.modules.order.mapper.OrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/30
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public PageInfo<Order> list(Integer userId, Integer pageNum, Integer pageSize) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
                () -> orderMapper.list(userId));
    }


    public int insertOrder(Order order) {
        return orderMapper.insert(order);
    }

}
