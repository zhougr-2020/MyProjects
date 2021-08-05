package com.nanhang.mybatis_plus.style.factory;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: immortal
 * @CreateDate: 2021/4/21 8:35
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "响应数据封装")
public class Result<T> implements Serializable {


    @ApiModelProperty(value = "响应状态码")
    private int code;

    @ApiModelProperty(value = "响应消息")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private T data;


    /**
     * 成功结果
     *
     * @param data 成功结果
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        return success(null);
    }


    /**
     * 失败返回结果
     *
     * @param code 错误码
     * @param msg  提示信息
     * @param <T>
     * @return
     */
    public static <T> Result error(Integer code, String msg) {
        return error(code, msg, null);
    }


    /**
     * 失败返回结果
     *
     * @param code 错误码
     * @param msg  提示信息
     * @param data 具体内容
     * @param <T>
     * @return
     */
    public static <T> Result error(Integer code, String msg, T data) {
        return new Result(code, msg, data);
    }

    /**
     * 失败返回结果
     *
     * @param msg  提示信息
     * @param <T>
     * @return
     */
    public static <T> Result error( String msg) {
        return new Result(ResultEnum.UNKNOWN_ERROR.getCode(), msg, null);
    }
}
