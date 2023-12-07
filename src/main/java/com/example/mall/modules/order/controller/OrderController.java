package com.example.mall.modules.order.controller;

import com.example.mall.modules.order.entity.Order;
import com.example.mall.modules.order.services.OrderService;
import com.example.mall.modules.user.entity.bo.UserBO;
import com.example.mall.pojo.ResultObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/30
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/list")
    public ResultObject list(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize) {
        UserBO userInfo = (UserBO) request.getAttribute("userInfo");
        PageInfo<Order> result = orderService.list(userInfo.getId(), pageNum, pageSize);
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("success");
        resultObject.setData(result);
        return resultObject;
    }

}
