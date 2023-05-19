package com.nozuch.securitydemo.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Result<T>{
    // 状态码
    private Integer code;
    // 提示信息
    private String msg;
    // 返回数据
    private T data;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(HttpStatus.OK.value(), data);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }
}
