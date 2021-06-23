package com.nanhang.mybatis_plus.pojo.taopiao;

import lombok.Data;

/**
 * @author: immortal
 * @CreateDate: 2021/2/3 15:58
 * @Description: X单信息
 */
@Data
public class XOrder {
    /**
     * x单号
     */
    private String orderNo;
    /**
     * x资源状态
     */
    private String dragonpassCardStatus;
    /**
     * 航段号
     */
    private String segOrder;


}
