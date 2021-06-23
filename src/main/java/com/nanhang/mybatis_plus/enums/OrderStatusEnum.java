package com.nanhang.mybatis_plus.enums;

/**
 * @author: immortal
 * @CreateDate: 2021/6/1 9:01
 * @Description:
 */
public enum OrderStatusEnum {

    UN_USE(1, "未使用"),

    FINISH_PAY(2, "已支付"),

    USED(3, "已使用");


    OrderStatusEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static OrderStatusEnum getOrderStatusEnum(Integer id) {
        if (id == null) {
            return null;
        }
        OrderStatusEnum[] orderStatusEnums = values();
        for (OrderStatusEnum orderStatusEnum : orderStatusEnums) {
            if (orderStatusEnum.getId() == id) {
                return orderStatusEnum;
            }
        }
        return null;
    }
}
