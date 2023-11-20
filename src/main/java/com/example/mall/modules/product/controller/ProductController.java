package com.example.mall.modules.product.controller;

import com.example.mall.modules.product.entity.Product;
import com.example.mall.pojo.ResultObject;
import com.example.mall.modules.product.services.ProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/6
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService goodsService;

    @RequestMapping("/list")
    public ResultObject list(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("name") String name) {
        PageInfo<Product> data = goodsService.list(pageNum, pageSize, name);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        resultObject.setData(data);
        return resultObject;
    }

    @RequestMapping("/add")
    public ResultObject add(Product product) {
        goodsService.add(product);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        return resultObject;
    }

    @RequestMapping("/update")
    public ResultObject update(Product product) {
        goodsService.update(product);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        return resultObject;
    }

    @RequestMapping("/delete")
    public ResultObject delete(Long id) {
        goodsService.delete(id);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        return resultObject;
    }

}