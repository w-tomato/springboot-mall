package com.example.mall.modules.goods.controller;

import com.example.mall.modules.goods.entity.Goods;
import com.example.mall.pojo.ResultObject;
import com.example.mall.modules.goods.services.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/6
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/list")
    public ResultObject list() {
        List<Goods> list = goodsService.list();
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        resultObject.setData(list);
        return resultObject;
    }

}
