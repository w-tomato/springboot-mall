package com.example.mall.modules.goods.mapper;

import com.example.mall.modules.goods.entity.Goods;

import java.util.List;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/6
 */
public interface GoodsMapper {

    List<Goods> selectAll();

}
