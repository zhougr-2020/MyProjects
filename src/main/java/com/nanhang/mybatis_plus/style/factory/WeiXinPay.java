package com.nanhang.mybatis_plus.style.factory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nanhang.mybatis_plus.pojo.taopiao.Flight;
import com.nanhang.mybatis_plus.pojo.taopiao.FlightWithXOrder;
import com.nanhang.mybatis_plus.pojo.taopiao.XOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: immortal
 * @CreateDate: 2021/4/20 9:21
 * @Description:
 */
@Service
@PayCode(value = "weixin", name = "微信")
public class WeiXinPay implements IPay {
    @Override
    public Result pay() {
        System.out.println("微信支付!!!!!!!!!!!!!!!!!!!!!!!!");
        LocalDateTime now = LocalDateTime.now();
        return new Result(1, "", now);
    }

    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("0.1");
        System.out.println(bigDecimal.multiply(new BigDecimal("0.1")));
        System.out.println(0.1 * 0.1);
        String result = "{\"data\":[{\"innerData\":{\"ticket\":{\"country\":\"CN\",\"fare\":{\"bSR\":0.0,\"netFareCurrency\":\"CNY\",\"fare\":4930.0,\"netFareAmount\":0.0,\"originCurrency\":\"CNY\",\"rOE\":0.0,\"additionalCollection\":0.0,\"calculationArea\":\"23MAY21CAN CZ SHE2440.00CZ CAN2490.00CNY4930.00END\",\"equivBalanceFarePD\":0.0,\"ticketAmount\":5090.0,\"bBR\":0.0,\"balanceFare\":0.0,\"equivCurrency\":\"CNY\",\"sellAmount\":0.0,\"currency\":\"CNY\",\"equivFarePD\":1.0,\"ticketId\":\"427491234\"},\"ticketNumber\":\"7844030028798\",\"documentType\":\"T\",\"issueTime\":\"2021-04-27 15:55:04\",\"issuedBy\":\"CHINA SOUTHERN AIRLINES WEB\",\"totalSegments\":2,\"tourCode\":\"B2C\",\"expiryDate\":1653580800000,\"placeOfIssue\":\"CZ CAN\",\"coupons\":[{\"flight\":{\"departureTime\":\"0805\",\"flightDate\":1621728300000,\"flightId\":\"593485393\",\"flightClass\":\"Y\",\"passengerNum\":0,\"couponId\":\"509217169\",\"flightNoSource\":\"ET\",\"destinationAirport\":\"SHE\",\"originAirport\":\"CAN\",\"carrier\":\"CZ\",\"marketingFlightClass\":\"Y\",\"flightNo\":\"6316\",\"flightDateSource\":\"ET\",\"stopLevel\":0,\"arrivalTime\":\"1140\",\"reservationsBooking\":\"Y\",\"flightClassSource\":\"ET\"},\"statusSource\":\"ET\",\"charge\":0.0,\"notValidBefore\":1621699200000,\"couponNumber\":1,\"repaymoney\":0.0,\"allowBaggage\":\"20K\",\"couponId\":\"509217169\",\"refundmoney\":0.0,\"costspend\":0.0,\"travelType\":\"1\",\"tkneFlag\":\"N\",\"fareBasis\":\"Y0401Y\",\"notValidAfter\":1621699200000,\"segmentId\":1,\"reservationStatus\":\"OK\",\"ticketId\":\"427491234\",\"status\":\"O\"}],\"originCity\":\"CAN\",\"pushTime\":1619510230000,\"taxs\":[{\"amount\":100.0,\"code\":\"CN\",\"taxId\":\"1049625890\",\"currency\":\"CNY\",\"ticketId\":\"427491234\"},{\"amount\":60.0,\"code\":\"YQ\",\"taxId\":\"1049625891\",\"currency\":\"CNY\",\"ticketId\":\"427491234\"},{\"amount\":0.0,\"code\":\"XT\",\"taxId\":\"1049625892\",\"currency\":\"CNY\",\"ticketId\":\"427491234\"}],\"issuingAgent\":{\"country\":\"CN\",\"agentId\":\"164188\",\"systemId\":\"CZECS\",\"iataCode\":\"00001704\",\"agentCity\":\"CAN\",\"issuingAgentId\":\"416965403\",\"ticketId\":\"427491234\",\"airport\":\"CAN\"},\"changeType\":\"I\",\"firstTicketId\":\"427491234\",\"dateOfIssue\":\"210427\",\"destinationCity\":\"CAN\",\"sequence\":1,\"insuranceFee\":0.0,\"reservations\":[{\"companyId\":\"CZ\",\"controlType\":\"1\",\"reservationId\":\"946434472\",\"contrlNumber\":\"MFFMM6\",\"ticketId\":\"427491234\"}],\"passenger\":{\"birthday\":978278400000,\"frequentFlyers\":[],\"certificateId\":\"110101200101010018\",\"certificateIdSource\":\"ET\",\"name\":\"雷霆嘎巴\",\"passengerId\":\"416964375\",\"certificateTypeSource\":\"ET\",\"type\":0,\"age\":0,\"ticketId\":\"427491234\",\"certificateType\":\"NI\"},\"endorsements\":\"BUCHAQIANZHUAN补差签转/BIANGENGTUIPIAOSHOUFEI变更退票收费\",\"totalTickets\":1,\"voucherPrintFlag\":\"N\",\"transactionTotal\":5090.0,\"pricing\":{\"internationalsale\":\"D\",\"ticketType\":\"3\",\"isGroup\":\"F\",\"ticketId\":\"427491234\"},\"ticketId\":\"427491234\"},\"dcsPnrs\":[],\"xOrders\":[{\"passengerName\":\"雷霆嘎巴\",\"deptTime\":\"08:05\",\"goodsId\":\"D210331000000341632\",\"idCard\":\"110101200101010018\",\"carbin\":\"Y\",\"dragonpassCardUpdateTime\":\"2021-04-27 15:56:41\",\"xType\":\"TRAFFIC\",\"compareKey\":1619510201000,\"dragonpassCard\":\"\",\"ticketNo\":\"7844030028798\",\"arrAirport\":\"SHE\",\"expireDate\":1619510201725,\"id\":\"22104270000265-1\",\"pushTime\":1619510070861,\"goodsName\":\"北京大兴机场-龙腾产品（国内）\",\"subGoodsName\":\"龙腾饭票\",\"deptAirport\":\"CAN\",\"orderNo\":\"22104270000265\",\"dragonpassCardStatus\":\"4\",\"flightKey\":\"CZ6316-20210523-CAN-SHE\",\"marketingId\":\"DXZDLQ\",\"flightNo\":\"CZ6316\",\"subGoodsId\":6655,\"segOrder\":\"1\",\"deptDate\":1621699200000}]},\"relationKey\":[\"TICKET-7844030028798\",\"FLN-CZ-6316\",\"ORG-CAN\",\"DST-SHE\"],\"pnrNo\":\"MFFMM6\",\"index\":202105231619510230,\"type\":\"ET\",\"objectId\":\"B2C_PRIMARY_ETS_7844030028798_1\"},{\"innerData\":{\"ticket\":{\"country\":\"CN\",\"fare\":{\"bSR\":0.0,\"netFareCurrency\":\"CNY\",\"fare\":4930.0,\"netFareAmount\":0.0,\"originCurrency\":\"CNY\",\"rOE\":0.0,\"additionalCollection\":0.0,\"calculationArea\":\"23MAY21CAN CZ SHE2440.00CZ CAN2490.00CNY4930.00END\",\"equivBalanceFarePD\":0.0,\"ticketAmount\":5090.0,\"bBR\":0.0,\"balanceFare\":0.0,\"equivCurrency\":\"CNY\",\"sellAmount\":0.0,\"currency\":\"CNY\",\"equivFarePD\":1.0,\"ticketId\":\"427491234\"},\"ticketNumber\":\"7844030028798\",\"documentType\":\"T\",\"issueTime\":\"2021-04-27 15:55:04\",\"issuedBy\":\"CHINA SOUTHERN AIRLINES WEB\",\"totalSegments\":2,\"tourCode\":\"B2C\",\"expiryDate\":1653580800000,\"placeOfIssue\":\"CZ CAN\",\"coupons\":[{\"flight\":{\"departureTime\":\"0805\",\"flightDate\":1621814700000,\"flightId\":\"593485394\",\"flightClass\":\"Y\",\"passengerNum\":0,\"couponId\":\"509217170\",\"flightNoSource\":\"ET\",\"destinationAirport\":\"CAN\",\"originAirport\":\"SHE\",\"carrier\":\"CZ\",\"marketingFlightClass\":\"Y\",\"flightNo\":\"6301\",\"flightDateSource\":\"ET\",\"stopLevel\":0,\"arrivalTime\":\"1210\",\"reservationsBooking\":\"Y\",\"flightClassSource\":\"ET\"},\"statusSource\":\"ET\",\"charge\":0.0,\"notValidBefore\":1621785600000,\"couponNumber\":2,\"repaymoney\":0.0,\"allowBaggage\":\"20K\",\"couponId\":\"509217170\",\"refundmoney\":0.0,\"costspend\":0.0,\"travelType\":\"1\",\"tkneFlag\":\"N\",\"fareBasis\":\"Y\",\"notValidAfter\":1621785600000,\"segmentId\":2,\"reservationStatus\":\"OK\",\"ticketId\":\"427491234\",\"status\":\"O\"}],\"originCity\":\"CAN\",\"pushTime\":1619510230000,\"taxs\":[{\"amount\":100.0,\"code\":\"CN\",\"taxId\":\"1049625890\",\"currency\":\"CNY\",\"ticketId\":\"427491234\"},{\"amount\":60.0,\"code\":\"YQ\",\"taxId\":\"1049625891\",\"currency\":\"CNY\",\"ticketId\":\"427491234\"},{\"amount\":0.0,\"code\":\"XT\",\"taxId\":\"1049625892\",\"currency\":\"CNY\",\"ticketId\":\"427491234\"}],\"issuingAgent\":{\"country\":\"CN\",\"agentId\":\"164188\",\"systemId\":\"CZECS\",\"iataCode\":\"00001704\",\"agentCity\":\"CAN\",\"issuingAgentId\":\"416965403\",\"ticketId\":\"427491234\",\"airport\":\"CAN\"},\"changeType\":\"I\",\"firstTicketId\":\"427491234\",\"dateOfIssue\":\"210427\",\"destinationCity\":\"CAN\",\"sequence\":1,\"insuranceFee\":0.0,\"reservations\":[{\"companyId\":\"CZ\",\"controlType\":\"1\",\"reservationId\":\"946434472\",\"contrlNumber\":\"MFFMM6\",\"ticketId\":\"427491234\"}],\"passenger\":{\"birthday\":978278400000,\"frequentFlyers\":[],\"certificateId\":\"110101200101010018\",\"certificateIdSource\":\"ET\",\"name\":\"雷霆嘎巴\",\"passengerId\":\"416964375\",\"certificateTypeSource\":\"ET\",\"type\":0,\"age\":0,\"ticketId\":\"427491234\",\"certificateType\":\"NI\"},\"endorsements\":\"BUCHAQIANZHUAN补差签转/BIANGENGTUIPIAOSHOUFEI变更退票收费\",\"totalTickets\":1,\"voucherPrintFlag\":\"N\",\"transactionTotal\":5090.0,\"pricing\":{\"internationalsale\":\"D\",\"ticketType\":\"3\",\"isGroup\":\"F\",\"ticketId\":\"427491234\"},\"ticketId\":\"427491234\"},\"dcsPnrs\":[],\"xOrders\":[{\"passengerName\":\"雷霆嘎巴\",\"deptTime\":\"08:05\",\"goodsId\":\"D210331000000341632\",\"idCard\":\"110101200101010018\",\"carbin\":\"Y\",\"dragonpassCardUpdateTime\":\"2021-04-27 15:56:41\",\"xType\":\"TRAFFIC\",\"compareKey\":1619510201000,\"dragonpassCard\":\"\",\"ticketNo\":\"7844030028798\",\"arrAirport\":\"CAN\",\"expireDate\":1619510201937,\"id\":\"22104270000266-2\",\"pushTime\":1619510071075,\"goodsName\":\"北京大兴机场-龙腾产品（国内）\",\"subGoodsName\":\"龙腾饭票\",\"deptAirport\":\"SHE\",\"orderNo\":\"22104270000266\",\"dragonpassCardStatus\":\"4\",\"flightKey\":\"CZ6301-20210524-SHE-CAN\",\"marketingId\":\"DXZDLQ\",\"flightNo\":\"CZ6301\",\"subGoodsId\":6655,\"segOrder\":\"2\",\"deptDate\":1621785600000}]},\"relationKey\":[\"TICKET-7844030028798\",\"FLN-CZ-6301\",\"ORG-SHE\",\"DST-CAN\"],\"pnrNo\":\"MFFMM6\",\"index\":202105241619510230,\"type\":\"ET\",\"objectId\":\"B2C_PRIMARY_ETS_7844030028798_2\"}],\"errorCode\":\"0\",\"errorMsg\":\"成功\",\"finish\":true,\"success\":true}\n";
        JSONObject resultJson = JSONObject.parseObject(result);
        List<FlightWithXOrder> flightWithXOrderList = new ArrayList<>();
        if (resultJson.getString("success").equals("true")) {
            JSONArray data = resultJson.getJSONArray("data");
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    FlightWithXOrder flightWithXOrder = new FlightWithXOrder();
                    JSONObject jsonObject = data.getJSONObject(i);
                    String type = jsonObject.getString("type");
                    if (type.equals("ET")) {
                        JSONObject innerData = jsonObject.getJSONObject("innerData");
                        Flight flight = JSON.parseObject(innerData.getJSONObject("ticket").getJSONArray("coupons").getJSONObject(0).getString("flight"), Flight.class);
                        JSONArray xOrdersJsonArray = innerData.getJSONArray("xOrders");
                        if (!CollectionUtils.isEmpty(xOrdersJsonArray)) {
                            XOrder xOrder = JSON.parseObject(xOrdersJsonArray.getString(0), XOrder.class);
                            flightWithXOrder.setXOrder(xOrder);
                        }
                        flightWithXOrder.setFlight(flight);
                    }
                    flightWithXOrderList.add(flightWithXOrder);
                }
                System.out.println(flightWithXOrderList);
                System.out.println(JSON.toJSONString(flightWithXOrderList));
            }
        }

    }
}
