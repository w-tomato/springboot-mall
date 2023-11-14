package com.example.mall.modules.user.entity.bo;

import com.example.mall.modules.user.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/7
 */
@Data
@Component
public class UserBO extends User {

    private List<String> roles;

}
