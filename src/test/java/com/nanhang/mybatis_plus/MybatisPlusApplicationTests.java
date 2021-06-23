package com.nanhang.mybatis_plus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nanhang.mybatis_plus.pojo.taopiao.Flight;
import com.nanhang.mybatis_plus.pojo.taopiao.Ticket;
import com.nanhang.mybatis_plus.pojo.taopiao.XOrder;
import com.nanhang.mybatis_plus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class MybatisPlusApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;


    @Test
    void contextLoad() {
        long start = System.currentTimeMillis();
        boolean flag = userService.insert(5);
        long end = System.currentTimeMillis();
        System.out.println(flag);
        System.out.println(end - start);
        //System.out.println(userService.findAll());
    }

    @Test
    void contextLoads() {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode("123");

        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void test1() {
//        redisTemplate.opsForValue().set("str", 0);
//        String str = (String) redisTemplate.opsForValue().get("str");
//        System.out.println(str);
        Long count = redisTemplate.opsForValue().increment("str", 1);
        System.out.println("自增后的值" + count);
    }

    @Test
    public void test2() {
        String result = "{\"data\":[{\"innerData\":{\"ticket\":{\"country\":\"CN\",\"fare\":{\"bSR\":0.0,\"netFareCurrency\":\"CNY\",\"fare\":1280.0,\"netFareAmount\":0.0,\"originCurrency\":\"CNY\",\"rOE\":0.0,\"additionalCollection\":0.0,\"calculationArea\":\"21MAR21CAN CZ SHA1280.00CNY1280.00ENDDSTCHSR178BR3x4tu010 \",\"equivBalanceFarePD\":0.0,\"ticketAmount\":1330.0,\"bBR\":0.0,\"balanceFare\":0.0,\"equivCurrency\":\"CNY\",\"sellAmount\":0.0,\"currency\":\"CNY\",\"equivFarePD\":1.0,\"ticketId\":\"427476278\"},\"ticketNumber\":\"7844032009995\",\"documentType\":\"T\",\"issueTime\":\"2021-03-04 17:16:25\",\"issuedBy\":\"CHINA SOUTHERN AIRLINES WEB\",\"totalSegments\":1,\"tourCode\":\"B2C\",\"expiryDate\":1649001600000,\"placeOfIssue\":\"CZ CAN\",\"coupons\":[{\"flight\":{\"departureTime\":\"0700\",\"flightDate\":1616281200000,\"flightId\":\"593464953\",\"flightClass\":\"Y\",\"passengerNum\":0,\"couponId\":\"509197022\",\"flightNoSource\":\"ET\",\"destinationAirport\":\"SHA\",\"originAirport\":\"CAN\",\"carrier\":\"CZ\",\"marketingFlightClass\":\"Y\",\"flightNo\":\"3533\",\"flightDateSource\":\"ET\",\"stopLevel\":0,\"arrivalTime\":\"0910\",\"reservationsBooking\":\"Y\",\"flightClassSource\":\"ET\"},\"statusSource\":\"ET\",\"charge\":0.0,\"notValidBefore\":1616256000000,\"couponNumber\":1,\"repaymoney\":0.0,\"allowBaggage\":\"20K\",\"couponId\":\"509197022\",\"refundmoney\":0.0,\"costspend\":0.0,\"travelType\":\"1\",\"tkneFlag\":\"N\",\"fareBasis\":\"YDSE0W05-YHQ\",\"notValidAfter\":1616256000000,\"segmentId\":1,\"reservationStatus\":\"OK\",\"ticketId\":\"427476278\",\"status\":\"O\"}],\"originCity\":\"CAN\",\"pushTime\":1614849460000,\"taxs\":[{\"amount\":50.0,\"code\":\"CN\",\"taxId\":\"1049582327\",\"currency\":\"CNY\",\"ticketId\":\"427476278\"},{\"amount\":0.0,\"code\":\"YQ\",\"taxId\":\"1049582328\",\"currency\":\"CNY\",\"ticketId\":\"427476278\"},{\"amount\":0.0,\"code\":\"XT\",\"taxId\":\"1049582329\",\"currency\":\"CNY\",\"ticketId\":\"427476278\"}],\"issuingAgent\":{\"country\":\"CN\",\"agentId\":\"164188\",\"systemId\":\"CZECS\",\"iataCode\":\"00001704\",\"agentCity\":\"CAN\",\"issuingAgentId\":\"416951156\",\"ticketId\":\"427476278\",\"airport\":\"CAN\"},\"changeType\":\"I\",\"firstTicketId\":\"427476278\",\"dateOfIssue\":\"210304\",\"destinationCity\":\"SHA\",\"sequence\":1,\"insuranceFee\":0.0,\"reservations\":[{\"companyId\":\"CZ\",\"controlType\":\"1\",\"reservationId\":\"946419811\",\"contrlNumber\":\"ME3H9W\",\"ticketId\":\"427476278\"}],\"passenger\":{\"birthday\":978278400000,\"frequentFlyers\":[],\"certificateId\":\"110101200101010018\",\"certificateIdSource\":\"ET\",\"name\":\"雷霆嘎巴\",\"passengerId\":\"416950128\",\"certificateTypeSource\":\"ET\",\"type\":0,\"age\":0,\"ticketId\":\"427476278\",\"certificateType\":\"NI\"},\"endorsements\":\"BUDEQIANZHUAN不得签转/BIANGENGTUIPIAOSHOUFEI变更退票收费\",\"totalTickets\":1,\"voucherPrintFlag\":\"N\",\"transactionTotal\":1330.0,\"pricing\":{\"internationalsale\":\"D\",\"ticketType\":\"3\",\"isGroup\":\"F\",\"ticketId\":\"427476278\"},\"ticketId\":\"427476278\"},\"dcsPnrs\":[],\"xOrders\":[{\"passengerName\":\"雷霆嘎巴\",\"deptAirport\":\"CAN\",\"orderNo\":\"22103040000163\",\"dragonpassCardStatus\":\"1\",\"deptTime\":\"07:00\",\"idCard\":\"110101200101010018\",\"carbin\":\"Y\",\"dragonpassCardUpdateTime\":\"2021-03-05 16:26:20.0\",\"flightKey\":\"3533-20210321-CAN-SHA\",\"compareKey\":1614932780000,\"flightNo\":\"3533\",\"dragonpassCard\":\"48SQNOK6UAQR\",\"ticketNo\":\"7844032009995\",\"segOrder\":\"1\",\"arrAirport\":\"SHA\",\"expireDate\":1614932785738,\"id\":\"22103040000163-1\",\"pushTime\":1614932785755,\"deptDate\":1616256000000},{\"passengerName\":\"雷霆嘎巴\",\"deptAirport\":\"CAN\",\"orderNo\":\"22103040000161\",\"dragonpassCardStatus\":\"1\",\"deptTime\":\"07:00\",\"idCard\":\"110101200101010018\",\"carbin\":\"Y\",\"dragonpassCardUpdateTime\":\"2021-03-05 16:41:40.0\",\"flightKey\":\"3533-20210321-CAN-SHA\",\"compareKey\":1614933700000,\"flightNo\":\"3533\",\"dragonpassCard\":\"D6UJS4YHYMJC\",\"ticketNo\":\"7844032009995\",\"segOrder\":\"1\",\"arrAirport\":\"SHA\",\"expireDate\":1614933703425,\"id\":\"22103040000161-1\",\"pushTime\":1614933703425,\"deptDate\":1616256000000}]},\"relationKey\":[\"TICKET-7844032009995\",\"FLN-CZ-3533\",\"ORG-CAN\",\"DST-SHA\"],\"pnrNo\":\"ME3H9W\",\"index\":202103211614849460,\"type\":\"ET\",\"objectId\":\"B2C_PRIMARY_ETS_7844032009995_1\"}],\"errorCode\":\"0\",\"errorMsg\":\"成功\",\"finish\":true,\"success\":true}";
        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.getString("success").equals("true")) {
            JSONArray data = resultJson.getJSONArray("data");
            if (data != null && data.size() > 0) {
                List<XOrder> list = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    String type = jsonObject.getString("type");
                    if (type.equals("ET")) {
                        JSONObject innerData = jsonObject.getJSONObject("innerData");
                        String xOrderJsonList = innerData.getString("xOrders");
                        if (!StringUtils.isEmpty(xOrderJsonList) && JSON.parseArray(xOrderJsonList).size() > 0) {
                            JSONArray xOrderList = JSON.parseArray(xOrderJsonList);
                            for (int j = 0; j < xOrderList.size(); j++) {
                                String xOrderJson = xOrderList.getString(j);
                                XOrder xOrder = JSON.parseObject(xOrderJson, XOrder.class);
                                list.add(xOrder);
                            }
                        }
                    }
                }
                System.out.println(list);
            }
        }

    }


    @Test
    public void test3() {
        String content = "{\"ticket\":{\"bookingAgents\":[],\"changeType\":\"E\",\"country\":\"CN\",\"coupons\":[{\"allowBaggage\":\"20K\",\"charge\":0.0,\"costspend\":0.0,\"couponId\":\"509211782\",\"couponNumber\":1,\"fareBasis\":\"YDSE0W05\",\"flight\":{\"arrivalTime\":\"1100\",\"carrier\":\"CZ\",\"couponId\":\"509211782\",\"departureTime\":\"0955\",\"destinationAirport\":\"SWA\",\"flightClass\":\"Y\",\"flightDate\":\"2021-04-17 09:55:00\",\"flightId\":\"593479912\",\"flightNo\":\"3892\",\"marketingFlightClass\":\"Y\",\"originAirport\":\"CAN\",\"reservationsBooking\":\"Y\",\"stopLevel\":0},\"issuedInExchangeFor\":\"7844030025599/1E\",\"notValidAfter\":\"2021-04-17 00:00:00\",\"notValidBefore\":\"2021-04-17 00:00:00\",\"previousStatus\":\"O\",\"refundmoney\":0.0,\"repaymoney\":0.0,\"reservationStatus\":\"OK\",\"segmentId\":1,\"settlementAuthoration\":\" 7841105110022\",\"status\":\"E\",\"ticketId\":\"427487296\",\"tkneFlag\":\"N\"}],\"dateOfIssue\":\"210415\",\"destinationCity\":\"SWA\",\"documentType\":\"T\",\"endorsements\":\"BUDEQIANZHUAN不得签转/BIANGENGTUIPIAOSHOUFEI变更退票收费\",\"expirydate\":\"2022-05-15 00:00:00\",\"fare\":{\"additionalCollection\":0.0,\"bBR\":0.0,\"bSR\":0.0,\"balanceFare\":0.0,\"calculationArea\":\"17APR21CAN CZ SWA1050.00CNY1050.00END\",\"currency\":\"CNY\",\"equivBalanceFarePD\":0.0,\"equivCurrency\":\"CNY\",\"equivFarePD\":1.0,\"fare\":1050.0,\"netFareAmount\":0.0,\"netFareCurrency\":\"CNY\",\"originCurrency\":\"CNY\",\"rOE\":0.0,\"sellAmount\":0.0,\"ticketAmount\":1100.0,\"ticketId\":\"427487296\"},\"firstTicketId\":\"427487296\",\"insuranceFee\":0.0,\"issueTime\":\"2021-04-15 11:06:57\",\"issuedBy\":\"CHINA SOUTHERN AIRLINES WEB\",\"issuingAgent\":{\"agentCity\":\"CAN\",\"agentId\":\"164184\",\"airport\":\"CAN\",\"country\":\"CN\",\"iataCode\":\"00001704\",\"issuingAgentId\":\"416961586\",\"systemId\":\"CZECS\",\"ticketId\":\"427487296\"},\"originCity\":\"CAN\",\"passenger\":{\"age\":0,\"birthday\":\"2001-01-01 00:00:00\",\"certificateId\":\"110101200101010018\",\"certificateType\":\"NI\",\"frequentFlyers\":[],\"name\":\"雷霆嘎巴\",\"passengerId\":\"416960558\",\"ticketId\":\"427487296\",\"type\":0},\"payments\":[{\"amount\":0.0,\"authorizedAmount\":0.0,\"paymentId\":\"418541905\",\"ticketId\":\"427487296\",\"type\":\"Card\"}],\"placeOfIssue\":\"CZ CAN\",\"pricing\":{\"internationalsale\":\"D\",\"isGroup\":\"F\",\"ticketId\":\"427487296\",\"ticketType\":\"3\"},\"reservations\":[{\"companyId\":\"CZ\",\"contrlNumber\":\"MCLH0V\",\"controlType\":\"1\",\"reservationId\":\"946430548\",\"ticketId\":\"427487296\"}],\"sequence\":1,\"taxs\":[{\"amount\":50.0,\"code\":\"CN\",\"currency\":\"CNY\",\"taxId\":\"1049613720\",\"ticketId\":\"427487296\"},{\"amount\":0.0,\"code\":\"YQ\",\"currency\":\"CNY\",\"taxId\":\"1049613721\",\"ticketId\":\"427487296\"},{\"amount\":0.0,\"code\":\"XT\",\"currency\":\"CNY\",\"taxId\":\"1049613722\",\"ticketId\":\"427487296\"}],\"ticketId\":\"427487296\",\"ticketNumber\":\"7844030025566\",\"totalSegments\":1,\"totalTickets\":1,\"tourCode\":\"B2C\",\"transactionTotal\":1100.0,\"voucherPrintFlag\":\"N\"},\"pushTime\":1618456400000}";
        boolean flag = false;
        if (!StringUtils.isEmpty(content)) {
            JSONObject json = JSONObject.parseObject(content);
            String ticketJson = json.getString("ticket");
            String pushTime = json.getString("pushTime");
            Ticket ticket = JSON.parseObject(ticketJson, Ticket.class);
            ticket.setPushTime(pushTime);
            JSONObject jsonObject = JSONObject.parseObject(ticketJson);
            String originalIssue = jsonObject.getString("originalIssue");
            JSONArray couponsArray = jsonObject.getJSONArray("coupons");
            Flight MQflight = null;
            String status = "";

            if (!CollectionUtils.isEmpty(couponsArray)) {
                for (int i = 0; i < couponsArray.size(); i++) {
                    JSONObject couponJson = couponsArray.getJSONObject(i);
                    status = couponJson.getString("status");
                    String issuedInExchangeFor = couponJson.getString("issuedInExchangeFor");
                    String flightJson = couponJson.getString("flight");
                    //变更单
                    if (status.equals("O") && !StringUtils.isEmpty(originalIssue)) {
                        MQflight = JSON.parseObject(flightJson, Flight.class);
                        MQflight.setStatus(status);
                        ticket.setFlight(MQflight);
                        break;
                    }
                    //客票不变的变更单
                    if (status.equals("E") && !StringUtils.isEmpty(originalIssue)) {
                        MQflight = JSON.parseObject(flightJson, Flight.class);
                        MQflight.setStatus(status);
                        ticket.setFlight(MQflight);
                        break;
                    }
                    //取消单
                    if (status.equals("R")) {
                        MQflight = JSON.parseObject(flightJson, Flight.class);
                        MQflight.setStatus(status);
                        ticket.setFlight(MQflight);
                        break;
                    }
                }
            }
            System.out.println(JSON.toJSONString(ticket));
        }
    }

    public static void main(String[] args) {
        String content = "{\"ticket\":{\"bookingAgents\":[],\"changeType\":\"E\",\"country\":\"CN\",\"coupons\":[{\"allowBaggage\":\"20K\",\"charge\":0.0,\"costspend\":0.0,\"couponId\":\"509211782\",\"couponNumber\":1,\"fareBasis\":\"YDSE0W05\",\"flight\":{\"arrivalTime\":\"1100\",\"carrier\":\"CZ\",\"couponId\":\"509211782\",\"departureTime\":\"0955\",\"destinationAirport\":\"SWA\",\"flightClass\":\"Y\",\"flightDate\":\"2021-04-17 09:55:00\",\"flightId\":\"593479912\",\"flightNo\":\"3892\",\"marketingFlightClass\":\"Y\",\"originAirport\":\"CAN\",\"reservationsBooking\":\"Y\",\"stopLevel\":0},\"issuedInExchangeFor\":\"7844030025599/1E\",\"notValidAfter\":\"2021-04-17 00:00:00\",\"notValidBefore\":\"2021-04-17 00:00:00\",\"previousStatus\":\"O\",\"refundmoney\":0.0,\"repaymoney\":0.0,\"reservationStatus\":\"OK\",\"segmentId\":1,\"settlementAuthoration\":\" 7841105110022\",\"status\":\"E\",\"ticketId\":\"427487296\",\"tkneFlag\":\"N\"}],\"dateOfIssue\":\"210415\",\"destinationCity\":\"SWA\",\"documentType\":\"T\",\"endorsements\":\"BUDEQIANZHUAN不得签转/BIANGENGTUIPIAOSHOUFEI变更退票收费\",\"expirydate\":\"2022-05-15 00:00:00\",\"fare\":{\"additionalCollection\":0.0,\"bBR\":0.0,\"bSR\":0.0,\"balanceFare\":0.0,\"calculationArea\":\"17APR21CAN CZ SWA1050.00CNY1050.00END\",\"currency\":\"CNY\",\"equivBalanceFarePD\":0.0,\"equivCurrency\":\"CNY\",\"equivFarePD\":1.0,\"fare\":1050.0,\"netFareAmount\":0.0,\"netFareCurrency\":\"CNY\",\"originCurrency\":\"CNY\",\"rOE\":0.0,\"sellAmount\":0.0,\"ticketAmount\":1100.0,\"ticketId\":\"427487296\"},\"firstTicketId\":\"427487296\",\"insuranceFee\":0.0,\"issueTime\":\"2021-04-15 11:06:57\",\"issuedBy\":\"CHINA SOUTHERN AIRLINES WEB\",\"issuingAgent\":{\"agentCity\":\"CAN\",\"agentId\":\"164184\",\"airport\":\"CAN\",\"country\":\"CN\",\"iataCode\":\"00001704\",\"issuingAgentId\":\"416961586\",\"systemId\":\"CZECS\",\"ticketId\":\"427487296\"},\"originCity\":\"CAN\",\"passenger\":{\"age\":0,\"birthday\":\"2001-01-01 00:00:00\",\"certificateId\":\"110101200101010018\",\"certificateType\":\"NI\",\"frequentFlyers\":[],\"name\":\"雷霆嘎巴\",\"passengerId\":\"416960558\",\"ticketId\":\"427487296\",\"type\":0},\"payments\":[{\"amount\":0.0,\"authorizedAmount\":0.0,\"paymentId\":\"418541905\",\"ticketId\":\"427487296\",\"type\":\"Card\"}],\"placeOfIssue\":\"CZ CAN\",\"pricing\":{\"internationalsale\":\"D\",\"isGroup\":\"F\",\"ticketId\":\"427487296\",\"ticketType\":\"3\"},\"reservations\":[{\"companyId\":\"CZ\",\"contrlNumber\":\"MCLH0V\",\"controlType\":\"1\",\"reservationId\":\"946430548\",\"ticketId\":\"427487296\"}],\"sequence\":1,\"taxs\":[{\"amount\":50.0,\"code\":\"CN\",\"currency\":\"CNY\",\"taxId\":\"1049613720\",\"ticketId\":\"427487296\"},{\"amount\":0.0,\"code\":\"YQ\",\"currency\":\"CNY\",\"taxId\":\"1049613721\",\"ticketId\":\"427487296\"},{\"amount\":0.0,\"code\":\"XT\",\"currency\":\"CNY\",\"taxId\":\"1049613722\",\"ticketId\":\"427487296\"}],\"ticketId\":\"427487296\",\"ticketNumber\":\"7844030025566\",\"totalSegments\":1,\"totalTickets\":1,\"tourCode\":\"B2C\",\"transactionTotal\":1100.0,\"voucherPrintFlag\":\"N\"},\"pushTime\":1618456400000}";
        boolean flag = false;
        if (!StringUtils.isEmpty(content)) {
            JSONObject json = JSONObject.parseObject(content);
            String ticketJson = json.getString("ticket");
            String pushTime = json.getString("pushTime");
            Ticket ticket = JSON.parseObject(ticketJson, Ticket.class);
            ticket.setPushTime(pushTime);
            JSONObject jsonObject = JSONObject.parseObject(ticketJson);
            String originalIssue = jsonObject.getString("originalIssue");
            JSONArray couponsArray = jsonObject.getJSONArray("coupons");
            Flight MQflight = null;
            String status = "";

            if (!CollectionUtils.isEmpty(couponsArray)) {
                for (int i = 0; i < couponsArray.size(); i++) {
                    JSONObject couponJson = couponsArray.getJSONObject(i);
                    status = couponJson.getString("status");
                    String issuedInExchangeFor = couponJson.getString("issuedInExchangeFor");
                    String flightJson = couponJson.getString("flight");
                    //变更单
                    if (status.equals("O") && !StringUtils.isEmpty(originalIssue)) {
                        MQflight = JSON.parseObject(flightJson, Flight.class);
                        MQflight.setStatus(status);
                        ticket.setFlight(MQflight);
                        break;
                    }
                    //客票不变的变更单
                    if (status.equals("E") && !StringUtils.isEmpty(originalIssue)) {
                        MQflight = JSON.parseObject(flightJson, Flight.class);
                        MQflight.setStatus(status);
                        ticket.setFlight(MQflight);
                        break;
                    }
                    //取消单
                    if (status.equals("R")) {
                        MQflight = JSON.parseObject(flightJson, Flight.class);
                        MQflight.setStatus(status);
                        ticket.setFlight(MQflight);
                        break;
                    }
                }
            }
            System.out.println(JSON.toJSONString(ticket));
        }
    }
}