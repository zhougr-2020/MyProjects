package com.nanhang.mybatis_plus.pojo.taopiao;

import lombok.Data;

import java.util.Date;

/**
 * @author: immortal
 * @CreateDate: 2021/2/3 9:57
 * @Description: 旅客信息
 */
@Data
public class Passenger {
    /**
     * 旅客id
     */
    private String passengerId;
    /**
     * 票号id
     */
    private String ticketId;
    /**
     * 旅客类别
     */
    private String type;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 旅客特殊信息
     */
    private Date specificData;
    /**
     * 旅客证件号
     */
    private String certificateId;
    /**
     * 证件类型
     */
    private String certificateType;
    /**
     * 携带婴儿标记
     */
    private String infantAccompany;
    /**
     * 航站楼信息标识
     */
    private String eirPrintClass;
    /**
     * 联系电话
     */
    private String homePhone;
    /**
     * 联系人
     */
    private String contactName;

    /**
     * X资源状态
     */
    private String dragonpassCardStatus;


}
