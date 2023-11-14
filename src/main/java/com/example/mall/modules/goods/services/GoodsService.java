package com.example.mall.modules.goods.services;

import com.example.mall.modules.goods.entity.Goods;
import com.example.mall.modules.goods.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/6
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<Goods> list() {
        return goodsMapper.selectAll();
    }

}
