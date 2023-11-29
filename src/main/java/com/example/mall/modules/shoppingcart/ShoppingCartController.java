package com.example.mall.modules.shoppingcart;

import com.example.mall.modules.product.services.ProductService;
import com.example.mall.modules.shoppingcart.entity.ShoppingCart;
import com.example.mall.modules.shoppingcart.services.ShoppingCartService;
import com.example.mall.modules.user.entity.User;
import com.example.mall.pojo.ResultObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author w-tomato
 * @description 购物车
 * @date 2023/11/28
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/list")
    public ResultObject list(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        User user = (User) request.getAttribute("userInfo");
        PageInfo<ShoppingCart> result = shoppingCartService.list(user.getId(), pageNum, pageSize);
        // 将ShoppingCart中的productId转换成Product对象
        List<ShoppingCart> shoppingCartList = result.getList();
        for (ShoppingCart shoppingCart : shoppingCartList) {
            shoppingCart.setProduct(productService.selectOne(shoppingCart.getProductId()));
        }
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        resultObject.setData(result);
        return resultObject;
    }

    @RequestMapping("/add")
    public ResultObject add(HttpServletRequest request, @RequestBody ShoppingCart shoppingCart) {
        User user = (User) request.getAttribute("userInfo");
        shoppingCart.setUserId(user.getId());
        shoppingCartService.add(shoppingCart);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        return resultObject;
    }

    @RequestMapping("/update")
    public ResultObject update(@RequestBody ShoppingCart shoppingCart) {
        shoppingCartService.update(shoppingCart);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        return resultObject;
    }

    @RequestMapping("/delete")
    public ResultObject delete(Integer id) {
        shoppingCartService.delete(id);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        return resultObject;
    }

}
