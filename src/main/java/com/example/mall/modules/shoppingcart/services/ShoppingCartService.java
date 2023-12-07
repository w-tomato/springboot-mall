package com.example.mall.modules.shoppingcart.services;

import com.example.mall.modules.order.entity.Order;
import com.example.mall.modules.order.services.OrderService;
import com.example.mall.modules.product.entity.Product;
import com.example.mall.modules.product.services.ProductService;
import com.example.mall.modules.shoppingcart.entity.ShoppingCart;
import com.example.mall.modules.shoppingcart.mapper.ShoppingCartMapper;
import com.example.mall.modules.user.entity.bo.UserBO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/28
 */
@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    public PageInfo<ShoppingCart> list(Integer userId, Integer pageNum, Integer pageSize) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
                () -> shoppingCartMapper.selectByUserId(userId));
    }

    public void add(ShoppingCart shoppingCart) {
        // 先根据用户ID和商品ID查询购物车是否已经存在该商品
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectByUserIdAndProductId(shoppingCart.getUserId(), shoppingCart.getProductId());
        if (shoppingCartList != null && shoppingCartList.size() > 0) {
            // 如果存在，则更新数量
            ShoppingCart shoppingCart1 = shoppingCartList.get(0);
            shoppingCart1.setQuantity(shoppingCart1.getQuantity() + shoppingCart.getQuantity());
            shoppingCartMapper.updateByPrimaryKey(shoppingCart1);
            return;
        }
        shoppingCartMapper.insert(shoppingCart);
    }

    public void update(ShoppingCart shoppingCart) {
        shoppingCartMapper.updateByPrimaryKey(shoppingCart);
    }

    public void delete(Integer id) {
        shoppingCartMapper.deleteByPrimaryKey(id);
    }

    public ShoppingCart selectOne(Integer id) {
        return shoppingCartMapper.selectByPrimaryKey(id);
    }

    public void submit(UserBO user, List<ShoppingCart> shoppingCartList) {
        // 根据商品ID查询商品详情后计算总价
        BigDecimal totalPrice = new BigDecimal(0);
        for (ShoppingCart shoppingCart : shoppingCartList) {
            Product product = productService.selectOne(shoppingCart.getProductId());
            BigDecimal multiply = product.getPrice().multiply(new BigDecimal(shoppingCart.getQuantity()));
            totalPrice =  totalPrice.add(multiply);
        }
        // 生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setStatus("已支付");
        order.setOrderDate(new Date());
        order.setPaymentMethod("微信支付");
        order.setShippingAddress(user.getShippingAddress());
        order.setCreateTime(new Date());
        order.setTotalAmount(totalPrice);
        orderService.insertOrder(order);
    }
}
