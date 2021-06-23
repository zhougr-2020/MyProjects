package com.nanhang.mybatis_plus.pojo.taopiao;

import lombok.Data;

/**
 * @author: immortal
 * @CreateDate: 2021/2/3 11:11
 * @Description: MQ接收消息实体
 */
@Data
public class Ticket {

    /**
     * 客票号
     */
    private String ticketNumber;
    /**
     * 变更类型:   I:出票  RF:退票  VOID:作废  E:换开  F:乘机  E1:航段变更  E2:姓名变更  E3:证件变更   E4:常旅客变更  E9:其他变更
     *            TY:打Y标识  TP:套票NoShow标识
     */
    private String changeType;

    /**
     * 客票详细信息
     */
    private Pricing pricing;
    /**
     * 旅客信息
     */
    private Passenger passenger;
    /**
     * 航班信息
     */
    private Flight flight;
    /**
     * 推送时间(时间戳)
     */
    private String pushTime;


}
