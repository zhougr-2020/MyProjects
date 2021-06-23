package com.nanhang.mybatis_plus.job.springtask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanhang.mybatis_plus.pojo.taopiao.Ticket;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: immortal
 * @CreateDate: 2021/1/8 9:18
 * @Description: 定时任务
 */
@Component
public class Task {

    @Scheduled(cron = "0/1 * *  * * ? ")
    public void  job(){
        System.out.println("hello");
    }


    public static void main(String[] args) {
        String content="{\"ticket\":{\"bookingAgents\":[],\"changeType\":\"E9\",\"country\":\"JP\",\"coupons\":[{\"allowBaggage\":\"2P\",\"charge\":0.0,\"controlCarrier\":\"NH\",\"costspend\":0.0,\"couponId\":\"509196329\",\"couponNumber\":1,\"fareBasis\":\"Y2XRTA3\",\"flight\":{\"carrier\":\"NH\",\"couponId\":\"509196329\",\"departureTime\":\"1000\",\"destinationAirport\":\"HGH\",\"flightClass\":\"Y\",\"flightDate\":\"2021-03-04 10:00:00\",\"flightId\":\"593464260\",\"flightNo\":\"929\",\"originAirport\":\"NRT\",\"stopLevel\":0,\"stopOver\":\"J\"},\"notValidAfter\":\"2022-03-04 00:00:00\",\"refundmoney\":0.0,\"repaymoney\":0.0,\"reservationStatus\":\"OK\",\"segmentId\":1,\"status\":\"N\",\"ticketId\":\"427475784\",\"tkneFlag\":\"N\"},{\"allowBaggage\":\"2P\",\"charge\":0.0,\"controlCarrier\":\"CA\",\"costspend\":0.0,\"couponId\":\"509196330\",\"couponNumber\":2,\"fareBasis\":\"Y2XRTA3\",\"flight\":{\"carrier\":\"CZ\",\"couponId\":\"509196330\",\"departureTime\":\"1525\",\"destinationAirport\":\"CAN\",\"flightClass\":\"E\",\"flightDate\":\"2021-03-06 15:25:00\",\"flightId\":\"593464261\",\"flightNo\":\"3512\",\"originAirport\":\"HGH\",\"stopLevel\":0,\"stopOver\":\"J\"},\"notValidAfter\":\"2022-03-04 00:00:00\",\"previousStatus\":\"O\",\"refundmoney\":0.0,\"repaymoney\":0.0,\"reservationStatus\":\"OK\",\"segmentId\":2,\"status\":\"A\",\"ticketId\":\"427475784\",\"tkneFlag\":\"N\"},{\"allowBaggage\":\"2P\",\"charge\":0.0,\"controlCarrier\":\"NH\",\"costspend\":0.0,\"couponId\":\"509196331\",\"couponNumber\":3,\"fareBasis\":\"Y2XRTA3\",\"flight\":{\"carrier\":\"NH\",\"couponId\":\"509196331\",\"departureTime\":\"0935\",\"destinationAirport\":\"NRT\",\"flightClass\":\"Y\",\"flightDate\":\"2021-03-09 09:35:00\",\"flightId\":\"593464262\",\"flightNo\":\"934\",\"originAirport\":\"CAN\",\"stopLevel\":0,\"stopOver\":\"J\"},\"notValidAfter\":\"2022-03-04 00:00:00\",\"refundmoney\":0.0,\"repaymoney\":0.0,\"reservationStatus\":\"OK\",\"segmentId\":3,\"status\":\"N\",\"ticketId\":\"427475784\",\"tkneFlag\":\"N\"}],\"dateOfIssue\":\"210304\",\"destinationCity\":\"TYO\",\"documentType\":\"T\",\"endorsements\":\"SKCHG\",\"expirydate\":\"2022-04-04 00:00:00\",\"fare\":{\"additionalCollection\":0.0,\"bBR\":0.0,\"bSR\":0.0,\"balanceFare\":0.0,\"calculationArea\":\"S-04MAR21TYO NH X/HGH CZ CAN M1502.09NH TYO1502.09NUC3004.18END ROE105.186400PD XT2130SW1000TK2320CN\",\"currency\":\"JPY\",\"equivBalanceFarePD\":0.0,\"equivFarePD\":0.0,\"fare\":316000.0,\"fareRemark\":\"TOTAL:NO ADC;\",\"netFareAmount\":0.0,\"originCurrency\":\"JPY\",\"rOE\":0.0,\"sellAmount\":0.0,\"ticketAmount\":0.0,\"ticketId\":\"427475784\"},\"firstTicketId\":\"427475784\",\"insuranceFee\":0.0,\"issueTime\":\"2021-03-04 08:15:04\",\"issuedBy\":\"ALL NIPPON AIRWAYS\",\"issuingAgent\":{\"agentCity\":\"MUC\",\"agentId\":\"148393\",\"agentType\":\"A\",\"airport\":\"TYO\",\"country\":\"JP\",\"iataCode\":\"16388750\",\"issuingAgentId\":\"416950715\",\"systemId\":\"1A\",\"ticketId\":\"427475784\"},\"originCity\":\"TYO\",\"originalIssue\":\" 2052416131036  TYO04MAR2116391351\",\"passenger\":{\"age\":0,\"certificateId\":\"022223333\",\"certificateType\":\"19\",\"frequentFlyers\":[],\"name\":\"AA/A MR\",\"passengerId\":\"416949687\",\"ticketId\":\"427475784\",\"type\":0},\"payments\":[{\"amount\":0.0,\"authorizedAmount\":0.0,\"companyId\":\"VI\",\"indicator\":\"5\",\"paymentId\":\"418531010\",\"ticketId\":\"427475784\",\"type\":\"Card\"}],\"placeOfIssue\":\"ALL NIPPON AIRWAYS ET SYNC\",\"pricing\":{\"internationalsale\":\"I\",\"ticketId\":\"427475784\",\"ticketType\":\"2\"},\"printLineText\":\"2052416131036W1ZEW2ZEW3ZE;\",\"reservations\":[{\"companyId\":\"1A\",\"contrlNumber\":\"633PQA\",\"controlType\":\"1\",\"reservationId\":\"946419339\",\"ticketId\":\"427475784\"},{\"companyId\":\"NH\",\"contrlNumber\":\"633PQA\",\"controlType\":\"1\",\"reservationId\":\"946419338\",\"ticketId\":\"427475784\"},{\"companyId\":\"CA\",\"contrlNumber\":\"NWW4HD\",\"controlType\":\"1\",\"reservationId\":\"946419337\",\"ticketId\":\"427475784\"}],\"sequence\":1,\"taxs\":[{\"amount\":0.0,\"category\":\"701\",\"code\":\"XT\",\"currency\":\"JPY\",\"taxId\":\"1049580922\",\"ticketId\":\"427475784\"},{\"amount\":530.0,\"category\":\"701\",\"code\":\"OI\",\"currency\":\"JPY\",\"taxId\":\"1049580921\",\"ticketId\":\"427475784\"},{\"amount\":200.0,\"category\":\"701\",\"code\":\"YQ\",\"currency\":\"JPY\",\"taxId\":\"1049580920\",\"ticketId\":\"427475784\"}],\"ticketId\":\"427475784\",\"ticketNumber\":\"2052416097381\",\"totalSegments\":1,\"totalTickets\":1,\"transactionTotal\":0.0,\"voucherPrintFlag\":\"N\"},\"pushTime\":1614818840000}";
        JSONObject json = JSONObject.parseObject(content);
        String ticketJson = json.getString("ticket");
        String pushTime = json.getString("pushTime");
        Ticket ticket = JSON.parseObject(ticketJson, Ticket.class);
        ticket.setPushTime(pushTime);
        System.out.println("客票MQ格式化消息Ticket--------->" + JSON.toJSONString(ticket));
    }
}
