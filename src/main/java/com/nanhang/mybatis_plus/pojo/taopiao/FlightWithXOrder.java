package com.nanhang.mybatis_plus.pojo.taopiao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: immortal
 * @CreateDate: 2021/4/22 9:40
 * @Description:
 */
@Data
public class FlightWithXOrder {
    private Flight flight;
    private XOrder xOrder;


    public static void main(String[] args) {
        String result = "{\"flight\":{\"arrivalTime\":\"1300\",\"carrier\":\"CZ\",\"couponId\":\"509213545\",\"departureTime\":\"0925\",\"destinationAirport\":\"SHE\",\"flightClass\":\"B\",\"flightDate\":1620696300000,\"flightId\":\"593481720\",\"flightNo\":\"6332\",\"marketingFlightClass\":\"B\",\"originAirport\":\"CAN\",\"reservationsBooking\":\"B\",\"stopLevel\":\"0\"},\"xOrder\":{\"dragonpassCardStatus\":\"0\",\"orderNo\":\"22104230000125\",\"segOrder\":\"1\"}}";
        JSONObject jsonObject1 = JSON.parseObject(result);
        String flight = jsonObject1.getString("flight");
        Flight flight1 = JSON.parseObject(flight, Flight.class);
        System.out.println("xck"+flight1.toString());


        String content = "{\"ticket\":{\"bookingAgents\":[],\"changeType\":\"RF\",\"country\":\"CN\",\"coupons\":[{\"allowBaggage\":\"20K\",\"charge\":0.0,\"costspend\":0.0,\"couponId\":\"509213545\",\"couponNumber\":1,\"fareBasis\":\"BGRN80\",\"flight\":{\"arrivalTime\":\"1300\",\"carrier\":\"CZ\",\"couponId\":\"509213545\",\"departureTime\":\"0925\",\"destinationAirport\":\"SHE\",\"flightClass\":\"B\",\"flightDate\":\"2021-05-11 09:25:00\",\"flightId\":\"593481720\",\"flightNo\":\"6332\",\"marketingFlightClass\":\"B\",\"originAirport\":\"CAN\",\"reservationsBooking\":\"B\",\"stopLevel\":0},\"notValidAfter\":\"2021-05-11 00:00:00\",\"notValidBefore\":\"2021-05-11 00:00:00\",\"previousStatus\":\"O\",\"refundmoney\":0.0,\"repaymoney\":0.0,\"reservationStatus\":\"OK\",\"segmentId\":1,\"settlementAuthoration\":\" 7841113161617\",\"status\":\"R\",\"ticketId\":\"427488548\",\"tkneFlag\":\"N\"}],\"dateOfIssue\":\"210421\",\"destinationCity\":\"SHE\",\"documentType\":\"T\",\"endorsements\":\"BUDEQIANZHUAN不得签转/BIANGENGTUIPIAOSHOUFEI变更退票收费\",\"expirydate\":\"2022-05-21 00:00:00\",\"fare\":{\"additionalCollection\":0.0,\"bBR\":0.0,\"bSR\":0.0,\"balanceFare\":0.0,\"calculationArea\":\"11MAY21CAN CZ SHE1870.00CNY1870.00END\",\"currency\":\"CNY\",\"equivBalanceFarePD\":0.0,\"equivCurrency\":\"CNY\",\"equivFarePD\":1.0,\"fare\":1870.0,\"netFareAmount\":0.0,\"netFareCurrency\":\"CNY\",\"originCurrency\":\"CNY\",\"rOE\":0.0,\"sellAmount\":0.0,\"ticketAmount\":1920.0,\"ticketId\":\"427488548\"},\"firstTicketId\":\"427488548\",\"insuranceFee\":0.0,\"issueTime\":\"2021-04-21 11:22:23\",\"issuedBy\":\"CHINA SOUTHERN AIRLINES WEB\",\"issuingAgent\":{\"agentCity\":\"CAN\",\"agentId\":\"164188\",\"airport\":\"CAN\",\"country\":\"CN\",\"iataCode\":\"00001704\",\"issuingAgentId\":\"416962804\",\"systemId\":\"CZECS\",\"ticketId\":\"427488548\"},\"originCity\":\"CAN\",\"passenger\":{\"age\":0,\"birthday\":\"1990-03-07 00:00:00\",\"certificateId\":\"110101199003076659\",\"certificateType\":\"NI\",\"frequentFlyers\":[],\"name\":\"张楠\",\"passengerId\":\"416961776\",\"ticketId\":\"427488548\",\"type\":0},\"payments\":[{\"amount\":0.0,\"authorizedAmount\":0.0,\"paymentId\":\"418543123\",\"ticketId\":\"427488548\",\"type\":\"Card\"}],\"placeOfIssue\":\"CZ CAN\",\"pricing\":{\"internationalsale\":\"D\",\"isGroup\":\"F\",\"ticketId\":\"427488548\",\"ticketType\":\"3\"},\"reservations\":[{\"companyId\":\"CZ\",\"contrlNumber\":\"MJQDF0\",\"controlType\":\"1\",\"reservationId\":\"946431822\",\"ticketId\":\"427488548\"}],\"sequence\":1,\"taxs\":[{\"amount\":50.0,\"code\":\"CN\",\"currency\":\"CNY\",\"taxId\":\"1049617765\",\"ticketId\":\"427488548\"},{\"amount\":0.0,\"code\":\"YQ\",\"currency\":\"CNY\",\"taxId\":\"1049617766\",\"ticketId\":\"427488548\"},{\"amount\":0.0,\"code\":\"XT\",\"currency\":\"CNY\",\"taxId\":\"1049617767\",\"ticketId\":\"427488548\"}],\"ticketId\":\"427488548\",\"ticketNumber\":\"7844030026599\",\"totalSegments\":1,\"totalTickets\":1,\"tourCode\":\"B2C\",\"transactionTotal\":1920.0,\"voucherPrintFlag\":\"N\"},\"pushTime\":1619166550000}";
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

        //MQ推送的航班信息集合
        List<Flight> MQFlightList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(couponsArray)) {
            for (int i = 0; i < couponsArray.size(); i++) {
                JSONObject couponJson = couponsArray.getJSONObject(i);
                status = couponJson.getString("status");
                String flightJson = couponJson.getString("flight");
                Flight flight2 = JSON.parseObject(flightJson, Flight.class);
                flight2.setStatus(status);
                MQFlightList.add(flight2);

            }
            System.out.println("mq"+MQFlightList.get(0).toString());
        }
        System.out.println(flight1.getFlightDate().compareTo(MQFlightList.get(0).getFlightDate()));
    }
}