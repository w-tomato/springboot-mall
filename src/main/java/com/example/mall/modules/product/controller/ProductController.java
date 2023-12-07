package com.example.mall.modules.product.controller;

import com.example.mall.modules.product.entity.Product;
import com.example.mall.modules.product.entity.ProductBO;
import com.example.mall.modules.product.entity.ProductCategory;
import com.example.mall.modules.product.entity.ProductInventory;
import com.example.mall.modules.product.mapper.ProductInventoryMapper;
import com.example.mall.modules.product.services.ProductCategoryService;
import com.example.mall.modules.product.services.ProductInventoryService;
import com.example.mall.modules.product.services.ProductService;
import com.example.mall.pojo.ResultObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/6
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInventoryService productInventoryService;

    @RequestMapping("/list")
    public ResultObject list(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("name") String name,
                             @RequestParam("status") String status) {
        PageInfo<ProductBO> data = productService.list(pageNum, pageSize, name, status);
        // 查询所有的产品分类
        if (!CollectionUtils.isEmpty(data.getList())) {
            List<Integer> categoryIdList = data.getList().stream().map(ProductBO::getCategoryId).distinct().collect(Collectors.toList());
            List<ProductCategory> productCategories = productCategoryService.selectByIdList(categoryIdList);
            data.getList().forEach(product -> {
                productCategories.forEach(productCategory -> {
                    if (product.getCategoryId().equals(productCategory.getId())) {
                        product.setCategoryName(productCategory.getCategoryName());
                    }
                });
            });
        }
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        resultObject.setData(data);
        return resultObject;
    }

    @RequestMapping("/get")
    public ResultObject get(@RequestParam("id") Long id) {
        Product product = productService.selectOne(id);
        // 查询库存
        ProductBO productBO = new ProductBO();
        BeanUtils.copyProperties(product, productBO);
        ProductInventory productInventory = productInventoryService.selectInventoryByProductId(product.getId());
        productBO.setInventory(productInventory == null ? 0 : productInventory.getQuantity());
        productBO.setCategoryName(productCategoryService.selectOne(product.getCategoryId()).getCategoryName());
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        resultObject.setData(productBO);
        return resultObject;
    }

    @RequestMapping("/add")
    public ResultObject add(@RequestBody Product product) {
        productService.add(product);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        return resultObject;
    }

    @RequestMapping("/update")
    public ResultObject update(@RequestBody Product product) {
        productService.update(product);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        return resultObject;
    }

    @RequestMapping("/delete")
    public ResultObject delete(@RequestBody Long id) {
        productService.delete(id);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        return resultObject;
    }

}
