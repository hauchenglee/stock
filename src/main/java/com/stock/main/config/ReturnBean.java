package com.stock.main.config;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class ReturnBean<T> {
    private int code = 0;
    private String message = "SUCCESS";
    private T data;

    private ReturnBean() {
    }

    public static <T> ReturnBean<T> success(Boolean data) {
        ReturnBean<T> returnBean = new ReturnBean<>();
        returnBean.code = ReturnCode.SUCCESS.getCode();
        returnBean.message = ReturnCode.SUCCESS.getMessage();
        returnBean.data = (T) data;
        return returnBean;
    }

    public static <T> ReturnBean<T> success(String data) {
        ReturnBean<T> returnBean = new ReturnBean<>();
        returnBean.code = ReturnCode.SUCCESS.getCode();
        returnBean.message = ReturnCode.SUCCESS.getMessage();
        returnBean.data = (T) data;
        return returnBean;
    }

    public static <T> ReturnBean<T> success(T data) {
        ReturnBean<T> returnBean = new ReturnBean<>();
        returnBean.code = ReturnCode.SUCCESS.getCode();
        returnBean.message = ReturnCode.SUCCESS.getMessage();
        returnBean.data = (T) data;
        return returnBean;
    }

    public static <T> ReturnBean<T> success(Optional<T> data) {
        ReturnBean<T> returnBean = new ReturnBean<>();
        returnBean.code = ReturnCode.SUCCESS.getCode();
        returnBean.message = ReturnCode.SUCCESS.getMessage();
        returnBean.data = (T) data;
        return returnBean;
    }

    public static <T> ReturnBean<T> success(List<T> dataList) {
        ReturnBean<T> returnBean = new ReturnBean<>();
        returnBean.code = ReturnCode.SUCCESS.getCode();
        returnBean.message = ReturnCode.SUCCESS.getMessage();
        returnBean.data = (T) dataList;
        return returnBean;
    }

    public static <T> ReturnBean<T> error(int code, String message) {
        ReturnBean<T> returnBean = new ReturnBean<>();
        returnBean.code = code;
        returnBean.message = message;
        return returnBean;
    }
}
