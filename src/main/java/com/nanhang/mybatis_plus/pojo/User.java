package com.nanhang.mybatis_plus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

//implements UserDetails
@Data
@ApiModel(value = "用户实体")
public class User {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户地址")
    private String address;

    @ApiModelProperty(value = "电话号码")
    private String telephone;

    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "身份证号码")
    private String idcard;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "注册账号时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createtime;


    //private List<Role> roles;

    //roles中获取当前用户所具有的角色，构造SimpleGrantedAuthority然后返回
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }


    //    public static void main(String[] args) {
//        String content="{\"ticket\":{\"bookingAgents\":[],\"changeType\":\"I\",\"country\":\"CN\",\"coupons\":[{\"allowBaggage\":\"20K\",\"charge\":0,\"costspend\":0,\"couponId\":\"509205970\",\"couponNumber\":1,\"fareBasis\":\"YDSE0W05\",\"flight\":{\"arrivalTime\":\"0945\",\"carrier\":\"CZ\",\"couponId\":\"509205970\",\"departureTime\":\"0750\",\"destinationAirport\":\"WUH\",\"flightClass\":\"Y\",\"flightDate\":\"2021-03-26 07:50:00\",\"flightId\":\"593474019\",\"flightNo\":\"3705\",\"marketingFlightClass\":\"Y\",\"originAirport\":\"CAN\",\"reservationsBooking\":\"Y\",\"stopLevel\":0},\"notValidAfter\":\"2021-03-26 00:00:00\",\"notValidBefore\":\"2021-03-26 00:00:00\",\"refundmoney\":0,\"repaymoney\":0,\"reservationStatus\":\"OK\",\"segmentId\":1,\"status\":\"O\",\"ticketId\":\"427482998\",\"tkneFlag\":\"N\"}],\"dateOfIssue\":\"210323\",\"destinationCity\":\"WUH\",\"documentType\":\"T\",\"endorsements\":\"NHKX BUDEQIANZHUAN不得签转/BIANGENGTUIPIAOSHOUFEI变更退票收费\",\"expirydate\":\"2022-04-23 00:00:00\",\"fare\":{\"additionalCollection\":0,\"bBR\":0,\"bSR\":0,\"balanceFare\":0,\"calculationArea\":\"26MAR21CAN CZ WUH1840.00CNY1840.00END\",\"currency\":\"CNY\",\"equivBalanceFarePD\":0,\"equivCurrency\":\"CNY\",\"equivFarePD\":0,\"fare\":1840,\"fareRemark\":\"TOTAL:NOADC\",\"netFareAmount\":0,\"netFareCurrency\":\"CNY\",\"originCurrency\":\"CNY\",\"rOE\":0,\"sellAmount\":0,\"ticketAmount\":0,\"ticketId\":\"427482998\"},\"firstTicketId\":\"427482998\",\"insuranceFee\":0,\"issueTime\":\"2021-03-23 10:29:30\",\"issuedBy\":\"CHINA SOUTHERN AIRLINES WEB\",\"issuingAgent\":{\"agentCity\":\"CAN\",\"agentId\":\"MOBILE\",\"airport\":\"CAN\",\"country\":\"CN\",\"iataCode\":\"00001704\",\"issuingAgentId\":\"416957489\",\"systemId\":\"CZECS\",\"ticketId\":\"427482998\"},\"originCity\":\"CAN\",\"originalIssue\":\"7844032015236CAN23MAR2021100001704\",\"passenger\":{\"age\":0,\"birthday\":\"2001-01-01 00:00:00\",\"certificateId\":\"110101200101010018\",\"certificateType\":\"NI\",\"frequentFlyers\":[],\"name\":\"雷霆嘎巴\",\"passengerId\":\"416956461\",\"ticketId\":\"427482998\",\"type\":0},\"payments\":[{\"amount\":0,\"authorizedAmount\":0,\"paymentId\":\"418537801\",\"ticketId\":\"427482998\",\"type\":\"Cash\"}],\"placeOfIssue\":\"CZ CAN\",\"pricing\":{\"internationalsale\":\"D\",\"isGroup\":\"F\",\"ticketId\":\"427482998\"},\"printLineText\":\"7844032015236W1ZE;\",\"reservations\":[{\"companyId\":\"CZ\",\"contrlNumber\":\"MBW4JC\",\"controlType\":\"1\",\"reservationId\":\"946426320\",\"ticketId\":\"427482998\"}],\"sequence\":1,\"taxs\":[{\"amount\":0,\"category\":\"701\",\"code\":\"CN\",\"currency\":\"CNY\",\"taxId\":\"1049601349\",\"ticketId\":\"427482998\"},{\"amount\":0,\"category\":\"701\",\"code\":\"YQ\",\"currency\":\"CNY\",\"taxId\":\"1049601350\",\"ticketId\":\"427482998\"}],\"ticketId\":\"427482998\",\"ticketNumber\":\"7844032015239\",\"totalSegments\":1,\"totalTickets\":1,\"transactionTotal\":0,\"voucherPrintFlag\":\"N\"},\"pushTime\":1616466590000}\n";
//        JSONObject json = JSONObject.parseObject(content);
//        String ticketJson = json.getString("ticket");
//        String pushTime = json.getString("pushTime");
//        Ticket ticket = JSON.parseObject(ticketJson, Ticket.class);
//        ticket.setPushTime(pushTime);
//        JSONObject jsonObject = JSONObject.parseObject(ticketJson);
//        String originalIssue = jsonObject.getString("originalIssue");
//        JSONArray couponsArray = jsonObject.getJSONArray("coupons");
//        Flight MQflight = null;
//        String status = "";
////        logger.info("客票MQ接收消息,客票号:{} -----------> {}", ticket.getTicketNumber(), content);
////        logger.info("客票MQ格式化消息Ticket--------->{}", JSON.toJSONString(ticket));
//        if (!CollectionUtils.isEmpty(couponsArray)) {
//            for (int i = 0; i < couponsArray.size(); i++) {
//                JSONObject couponJson = couponsArray.getJSONObject(i);
//                status = couponJson.getString("status");
//                String issuedInExchangeFor = couponJson.getString("issuedInExchangeFor");
//                String flightJson = couponJson.getString("flight");
//                //变更单
//                if (status.equals("O") && !StringUtils.isEmpty(originalIssue)) {
//                    MQflight = JSON.parseObject(flightJson, Flight.class);
//                    MQflight.setStatus(status);
//                    ticket.setFlight(MQflight);
//                    break;
//                }
//                //取消单
//                if (status.equals("R")) {
//                    MQflight = JSON.parseObject(flightJson, Flight.class);
//                    MQflight.setStatus(status);
//                    ticket.setFlight(MQflight);
//                    break;
//                }
//            }
//        }
//        //原客票号
//        String primaryTicketNumber = "";
//        if (status.equals("O") && !StringUtils.isEmpty(originalIssue) && Pattern.matches("784\\d{10}$", originalIssue.substring(0, 13))) {
//            primaryTicketNumber = originalIssue.substring(0, 13);
//        }
//
//        if (status.equals("R")) {
//            primaryTicketNumber = ticket.getTicketNumber();
//        }
//
//        System.out.println(primaryTicketNumber);
//        System.out.println(JSON.toJSONString(ticket));
//        String str=null;
//        System.out.println(" afaf".equals(str));
//    }
    public static void main(String[] args) {
        User user = new User();
        System.out.println(user.getSex());
    }
}
