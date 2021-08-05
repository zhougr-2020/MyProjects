package com.nanhang.mybatis_plus.style.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum  {

    UNKNOWN_ERROR(-1, "未知错误"),

    SUCCESS(0, "成功"),

    PARAM_ERROR(1, "参数不正确"),

    UPDATE_ERROR(2, "更新数据失败"),

    SAVE_ERROR(3, "更新数据失败"),

    LOGIN_FAIL(25, "登录失败, 登录信息不正确"),

    VALIDATE_FAILED(404, "参数检验失败"),

    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    FORBIDDEN(403, "没有相关权限"),

    METHOD_NOT_ALLOWED(405, "不支持当前请求方法"),

    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),

    UNPROCESSABLE_ENTITY(422, "所上传文件大小超过最大限制，上传失败"),

    INTERNAL_SERVER_ERROR(500, "服务内部异常"),

    CANNOT_MODIFY_IN_AUDIT(700, "审核中无法修改"),


    OPR_FAIL(10001, "操作失败"),

    LOGIN_NAME_EXIST(10002, "该登录名已存在"),
    CUSTOMER_CODE_EXIST(10004,"该客户代码已存在"),
    NAME_EXIST(10003, "姓名已存在"),
    MAKE_INVOICE_CONDITION_1(10004, "开票申请审核通过才可操作"),
    MAKE_INVOICE_CONDITION_2(10005, "付款申请审核通过才可操作"),
    MAKE_INVOICE_CONDITION_3(10006, "核销总金额超过申请金额"),
    ENCODE_PURE_NUMBERS(10007, "六联单号须为13位的纯数字"),
    IS_CONFIRM_JIE_DAN(10008, "司机未进行接单操作，不能反馈车辆提货状态"),
    CUSTOMER_NAME_EXIST(10009, "客户名称重复"),
    SUPPLIER_NAME_EXIST(10010, "供应商名称重复"),
    VIVO_ERROR(1000001, "调用vivo接口失败"),

    ;
    private Integer code;
    private String message;

}
