package com.example.mall.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/7
 */
@Data
@Component
public class ResultObject {

    private Integer code;

    private String message;

    private Object data;

    public ResultObject() {
    }

    public ResultObject(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
