package com.example.mall.modules.goods.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/6
 */
@Data
public class Goods {

    private Long id;

    private String name;

    private Long price;

    private String intro;

    private String coverImage;

    private Integer remain;

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", intro='" + intro + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", remain=" + remain +
                '}';
    }
}
