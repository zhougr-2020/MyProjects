package com.nanhang.mybatis_plus.pojo.taopiao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: immortal
 * @CreateDate: 2021/2/3 9:56
 * @Description: 航班信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    /**
     * 航段id
     */
    private String flightId;
    /**
     * 票联id
     */
    private String couponId;
    /**
     * 航班号
     */
    private String flightNo;
    /**
     * 承运人
     */
    private String carrier;
    /**
     * 起飞日期
     */
    private Date flightDate;
    /**
     * 始发机场
     */
    private String originAirport;
    /**
     * 到达机场
     */
    private String destinationAirport;
    /**
     * 出发时间
     */
    private String departureTime;
    /**
     * 航班到达时间
     */
    private String arrivalTime;
    /**
     * 订座标识
     */
    private String reservationsBooking;
    /**
     * 舱位等级
     */
    private String flightClass;
    /**
     * 中途停留次数
     */
    private String stopLevel;
    /**
     * 中转标记
     */
    private String STOPOVER;
    /**
     * 销售承运人
     */
    private String marketingCarrier;
    /**
     * 销售承运人航班号
     */
    private String marketingFlightNo;
    /**
     * 销售航班仓位
     */
    private String marketingFlightClass;
    /**
     * 到达日期
     */
    private String arrivalDate;

    /**
     * 航段号
     */
    private Integer couponNumber;

    /**
     * 客票状态
     */
    private String status;
}
