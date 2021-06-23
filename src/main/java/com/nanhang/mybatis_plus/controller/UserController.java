package com.nanhang.mybatis_plus.controller;

import com.nanhang.mybatis_plus.annotation.LoginCheck;
import com.nanhang.mybatis_plus.pojo.User;
import com.nanhang.mybatis_plus.service.UserService;
import com.nanhang.mybatis_plus.style.factory.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户控制器")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation("所有列表")
    @GetMapping("/all")
    @LoginCheck(check = false)
    public List<User> findAll(HttpServletRequest request) {
        System.out.println(request.getRequestURL().toString());
//        ResponseEntity<Map> url = new RestTemplate().getForEntity("url", Map.class);
        //       Map body = url.getBody();
        new ArrayList<Integer>().sort((o1, o2) -> o1 - o2);
        return userService.findAll();
    }

    @ApiOperation("根据id查询用户")
    @GetMapping("/one/{id}")
    public User findOne(@Valid @PathVariable Integer id) {
        return userService.findOne(id);
    }

    @ApiOperation("插入用户")
    @GetMapping("/insert")
    public int save() {
        return userService.save();
    }

    @ApiOperation("根据名字查找用户")
    @GetMapping("/findByUser")
    public User findById(String username) {
        return userService.findByUser(username);
    }

    @ApiOperation("效验注解")
    @PostMapping("/check")
    public Result check(@RequestBody @Valid User user){
        return userService.check(user);
    }



    //导出excle
    @GetMapping("/excel")
    public void getExcel(HttpServletResponse response, HttpServletRequest request) {
        List<User> userList = userService.findAll();
        //1.在内存中创建一个excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //2.创建工作簿
        HSSFSheet sheet = hssfWorkbook.createSheet();
        //3.创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("id");
        titleRow.createCell(1).setCellValue("姓名");
        titleRow.createCell(2).setCellValue("地址");
        titleRow.createCell(3).setCellValue("电话");

        if (!CollectionUtils.isEmpty(userList)) {
            for (User user : userList) {
                //获取最后一行的行号
                int lastRowNum = sheet.getLastRowNum();
                HSSFRow sheetRow = sheet.createRow(lastRowNum + 1);
                sheetRow.createCell(0).setCellValue(user.getId());
                sheetRow.createCell(1).setCellValue(user.getUsername());
                sheetRow.createCell(2).setCellValue(user.getAddress());
                sheetRow.createCell(3).setCellValue(user.getTelephone());
            }
        }
        //创建文件名
        String fileName = "用户注册表";
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1") + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls");
            hssfWorkbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //读取excle
    @PostMapping("readExcel")
    public void readExcel(MultipartFile file, HttpServletRequest request) throws IOException {
        //封装读取的数据
        List<User> list = new ArrayList<>();
        if (file == null) {
            log.info("文件不存在");
            throw new FileNotFoundException("文件不存在");
        }
        java.lang.String fileName = file.getOriginalFilename();
        if (!Objects.requireNonNull(fileName).endsWith("xls") && !fileName.endsWith("xlsx")) {
            log.info(fileName + "不是excle文件");
            throw new IOException(fileName + "不是excle文件");
        }
        InputStream is = file.getInputStream();
        Workbook workbook = null;
        //根据文件后缀名不同(xls和xlsx)获得不同的Workbook工作簿类对象
        if (fileName.endsWith("xls")) {
            //2003
            workbook = new HSSFWorkbook(is);
        } else if (fileName.endsWith("xlsx")) {
            //2007
            workbook = new XSSFWorkbook(is);
        }
        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet != null) {
                int firstRowNum = sheet.getFirstRowNum();//开始行
                int lastRowNum = sheet.getLastRowNum(); //结束行

                //不读取第一行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    User user = new User();
                    user.setId(Integer.parseInt(getCellValue(row.getCell(0))));
                    user.setUsername(getCellValue(row.getCell(1)));
                    user.setAddress(getCellValue(row.getCell(2)));
                    user.setTelephone(getCellValue(row.getCell(3)));
                    list.add(user);
                    log.info("--------------------------->" + user.toString());
                }
                if (!CollectionUtils.isEmpty(list)) {
                    userService.update(list);
                }


            }
        }

    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    //time1>time2 返回true
    public boolean compare(String time1, String time2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //将字符串形式的时间转化为Date类型的时间
            Date a = sdf.parse(time1);
            Date b = sdf.parse(time2);
            // 根据将Date转换成毫秒
            return a.getTime() - b.getTime() > 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


//    public static void main(String[] args) {
//        String result="{\"data\":[{\"innerData\":{\"ticket\":{\"country\":\"CN\",\"fare\":{\"bSR\":0.0,\"netFareCurrency\":\"CNY\",\"fare\":1710.0,\"netFareAmount\":0.0,\"originCurrency\":\"CNY\",\"rOE\":0.0,\"additionalCollection\":0.0,\"calculationArea\":\"11MAY21CAN CZ PVG1180.00CZ CAN530.00CNY1710.00END\",\"equivBalanceFarePD\":0.0,\"ticketAmount\":1850.0,\"bBR\":0.0,\"balanceFare\":0.0,\"equivCurrency\":\"CNY\",\"sellAmount\":0.0,\"currency\":\"CNY\",\"equivFarePD\":1.0,\"ticketId\":\"427493818\"},\"ticketNumber\":\"7844032018822\",\"documentType\":\"T\",\"issueTime\":\"2021-05-11 10:56:14\",\"issuedBy\":\"CHINA SOUTHERN AIRLINES WEB\",\"totalSegments\":2,\"tourCode\":\"B2C\",\"expiryDate\":1654876800000,\"placeOfIssue\":\"CZ CAN\",\"coupons\":[{\"flight\":{\"departureTime\":\"1800\",\"flightDate\":1620727200000,\"flightId\":\"593488729\",\"flightClass\":\"Y\",\"passengerNum\":0,\"couponId\":\"509220459\",\"flightNoSource\":\"ET\",\"destinationAirport\":\"PVG\",\"originAirport\":\"CAN\",\"carrier\":\"CZ\",\"marketingFlightClass\":\"Y\",\"flightNo\":\"8211\",\"flightDateSource\":\"ET\",\"stopLevel\":0,\"arrivalTime\":\"2030\",\"reservationsBooking\":\"Y\",\"flightClassSource\":\"ET\"},\"statusSource\":\"ET\",\"charge\":0.0,\"notValidBefore\":1620662400000,\"couponNumber\":1,\"repaymoney\":0.0,\"allowBaggage\":\"20K\",\"couponId\":\"509220459\",\"refundmoney\":0.0,\"costspend\":0.0,\"travelType\":\"3\",\"tkneFlag\":\"N\",\"fareBasis\":\"Y214\",\"controlCarrier\":\"CZ\",\"notValidAfter\":1620662400000,\"segmentId\":1,\"reservationStatus\":\"OK\",\"ticketId\":\"427493818\",\"previousStatus\":\"O\",\"status\":\"F\"}],\"originCity\":\"CAN\",\"pushTime\":1620782060001,\"taxs\":[{\"amount\":100.0,\"code\":\"CN\",\"taxId\":\"1049633145\",\"currency\":\"CNY\",\"ticketId\":\"427493818\"},{\"amount\":40.0,\"code\":\"YQ\",\"taxId\":\"1049633146\",\"currency\":\"CNY\",\"ticketId\":\"427493818\"},{\"amount\":0.0,\"code\":\"XT\",\"taxId\":\"1049633147\",\"currency\":\"CNY\",\"ticketId\":\"427493818\"}],\"issuingAgent\":{\"country\":\"CN\",\"agentId\":\"164188\",\"systemId\":\"CZECS\",\"iataCode\":\"00001704\",\"agentCity\":\"CAN\",\"issuingAgentId\":\"416967873\",\"ticketId\":\"427493818\",\"airport\":\"CAN\"},\"changeType\":\"F\",\"firstTicketId\":\"427493818\",\"dateOfIssue\":\"210511\",\"destinationCity\":\"CAN\",\"sequence\":1,\"insuranceFee\":0.0,\"reservations\":[{\"companyId\":\"CZ\",\"controlType\":\"1\",\"reservationId\":\"946437008\",\"contrlNumber\":\"MJ2HKR\",\"ticketId\":\"427493818\"}],\"passenger\":{\"birthday\":978278400000,\"frequentFlyers\":[],\"certificateId\":\"110101200101010157\",\"certificateIdSource\":\"ET\",\"name\":\"雷霆嘎巴\",\"passengerId\":\"416966845\",\"certificateTypeSource\":\"ET\",\"type\":0,\"age\":0,\"ticketId\":\"427493818\",\"certificateType\":\"NI\"},\"endorsements\":\"BUDEQIANZHUAN不得签转/BIANGENGTUIPIAOSHOUFEI变更退票收费\",\"totalTickets\":1,\"voucherPrintFlag\":\"N\",\"transactionTotal\":1850.0,\"pricing\":{\"internationalsale\":\"D\",\"ticketType\":\"3\",\"isGroup\":\"F\",\"ticketId\":\"427493818\"},\"ticketId\":\"427493818\"},\"dcsPnrs\":[],\"xOrders\":[{\"passengerName\":\"雷霆嘎巴\",\"deptTime\":\"18:00\",\"goodsId\":\"D1907090000002\",\"idCard\":\"110101200101010157\",\"carbin\":\"Y\",\"dragonpassCardUpdateTime\":\"2021-05-12 01:01:09.0\",\"xType\":\"PRIVATECAR\",\"compareKey\":1620752469000,\"dragonpassCard\":\"CD7REO8V7B1R\",\"ticketNo\":\"7844032018822\",\"arrAirport\":\"PVG\",\"expireDate\":1620752488512,\"id\":\"22105110000139-1\",\"pushTime\":1620752470736,\"goodsName\":\"北京大兴机场-专车券（国内）\",\"subGoodsName\":\"10元专车券（需提前30分钟约车）\",\"deptAirport\":\"CAN\",\"orderNo\":\"22105110000139\",\"dragonpassCardStatus\":\"3\",\"flightKey\":\"CZ8211-20210511-CAN-PVG\",\"marketingId\":\"TESTLHMXLQZ00\",\"flightNo\":\"CZ8211\",\"subGoodsId\":5621,\"segOrder\":\"1\",\"deptDate\":1620662400000},{\"passengerName\":\"\",\"deptTime\":\"18:00\",\"goodsId\":\"D2002250000001\",\"idCard\":\"110101200101010157\",\"carbin\":\"Y\",\"dragonpassCardUpdateTime\":\"2021-05-11 14:10:06.0\",\"xType\":\"HOTELCOUPON\",\"compareKey\":1620713406000,\"dragonpassCard\":\"OGDVR8L5DPWL\",\"ticketNo\":\"7844032018822\",\"arrAirport\":\"PVG\",\"expireDate\":1620713425593,\"id\":\"22105110000142-1\",\"pushTime\":1620713407934,\"goodsName\":\"货架专用-北京大兴机场-酒店券（国际）\",\"subGoodsName\":\"1元酒店券\",\"deptAirport\":\"CAN\",\"orderNo\":\"22105110000142\",\"dragonpassCardStatus\":\"0\",\"flightKey\":\"CZ8211-20210511-CAN-PVG\",\"marketingId\":\"TESTLHLQ00\",\"flightNo\":\"CZ8211\",\"subGoodsId\":5961,\"segOrder\":\"1\",\"deptDate\":1620662400000}]},\"relationKey\":[\"TICKET-7844032018822\",\"FLN-CZ-8211\",\"ORG-CAN\",\"DST-PVG\"],\"pnrNo\":\"MJ2HKR\",\"index\":202105111620782060,\"type\":\"ET\",\"objectId\":\"B2C_PRIMARY_ETS_7844032018822_1\"},{\"innerData\":{\"ticket\":{\"country\":\"CN\",\"fare\":{\"bSR\":0.0,\"netFareCurrency\":\"CNY\",\"fare\":1710.0,\"netFareAmount\":0.0,\"originCurrency\":\"CNY\",\"rOE\":0.0,\"additionalCollection\":0.0,\"calculationArea\":\"11MAY21CAN CZ PVG1180.00CZ CAN530.00CNY1710.00END\",\"equivBalanceFarePD\":0.0,\"ticketAmount\":1850.0,\"bBR\":0.0,\"balanceFare\":0.0,\"equivCurrency\":\"CNY\",\"sellAmount\":0.0,\"currency\":\"CNY\",\"equivFarePD\":1.0,\"ticketId\":\"427493818\"},\"ticketNumber\":\"7844032018822\",\"documentType\":\"T\",\"issueTime\":\"2021-05-11 10:56:14\",\"issuedBy\":\"CHINA SOUTHERN AIRLINES WEB\",\"totalSegments\":2,\"tourCode\":\"B2C\",\"expiryDate\":1654876800000,\"placeOfIssue\":\"CZ CAN\",\"coupons\":[{\"flight\":{\"departureTime\":\"0655\",\"flightDate\":1620773700000,\"flightId\":\"593488730\",\"flightClass\":\"H\",\"passengerNum\":0,\"couponId\":\"509220460\",\"flightNoSource\":\"ET\",\"destinationAirport\":\"CAN\",\"originAirport\":\"PVG\",\"carrier\":\"CZ\",\"marketingFlightClass\":\"H\",\"flightNo\":\"3549\",\"flightDateSource\":\"ET\",\"stopLevel\":0,\"arrivalTime\":\"0925\",\"reservationsBooking\":\"H\",\"flightClassSource\":\"ET\"},\"statusSource\":\"ET\",\"charge\":0.0,\"notValidBefore\":1620748800000,\"couponNumber\":2,\"repaymoney\":0.0,\"allowBaggage\":\"20K\",\"couponId\":\"509220460\",\"refundmoney\":0.0,\"costspend\":0.0,\"travelType\":\"1\",\"tkneFlag\":\"N\",\"fareBasis\":\"HMIZWNNC\",\"controlCarrier\":\"CZ\",\"notValidAfter\":1620748800000,\"segmentId\":2,\"reservationStatus\":\"OK\",\"ticketId\":\"427493818\",\"previousStatus\":\"A\",\"status\":\"O\"}],\"originCity\":\"CAN\",\"pushTime\":1620782060001,\"taxs\":[{\"amount\":100.0,\"code\":\"CN\",\"taxId\":\"1049633145\",\"currency\":\"CNY\",\"ticketId\":\"427493818\"},{\"amount\":40.0,\"code\":\"YQ\",\"taxId\":\"1049633146\",\"currency\":\"CNY\",\"ticketId\":\"427493818\"},{\"amount\":0.0,\"code\":\"XT\",\"taxId\":\"1049633147\",\"currency\":\"CNY\",\"ticketId\":\"427493818\"}],\"issuingAgent\":{\"country\":\"CN\",\"agentId\":\"164188\",\"systemId\":\"CZECS\",\"iataCode\":\"00001704\",\"agentCity\":\"CAN\",\"issuingAgentId\":\"416967873\",\"ticketId\":\"427493818\",\"airport\":\"CAN\"},\"changeType\":\"F\",\"firstTicketId\":\"427493818\",\"dateOfIssue\":\"210511\",\"destinationCity\":\"CAN\",\"sequence\":1,\"insuranceFee\":0.0,\"reservations\":[{\"companyId\":\"CZ\",\"controlType\":\"1\",\"reservationId\":\"946437008\",\"contrlNumber\":\"MJ2HKR\",\"ticketId\":\"427493818\"}],\"passenger\":{\"birthday\":978278400000,\"frequentFlyers\":[],\"certificateId\":\"110101200101010157\",\"certificateIdSource\":\"ET\",\"name\":\"雷霆嘎巴\",\"passengerId\":\"416966845\",\"certificateTypeSource\":\"ET\",\"type\":0,\"age\":0,\"ticketId\":\"427493818\",\"certificateType\":\"NI\"},\"endorsements\":\"BUDEQIANZHUAN不得签转/BIANGENGTUIPIAOSHOUFEI变更退票收费\",\"totalTickets\":1,\"voucherPrintFlag\":\"N\",\"transactionTotal\":1850.0,\"pricing\":{\"internationalsale\":\"D\",\"ticketType\":\"3\",\"isGroup\":\"F\",\"ticketId\":\"427493818\"},\"ticketId\":\"427493818\"},\"dcsPnrs\":[],\"xOrders\":[{\"passengerName\":\"雷霆嘎巴\",\"deptTime\":\"06:55\",\"goodsId\":\"D1907090000002\",\"idCard\":\"110101200101010157\",\"carbin\":\"H\",\"dragonpassCardUpdateTime\":\"2021-05-12 01:01:09.0\",\"xType\":\"PRIVATECAR\",\"compareKey\":1620752469000,\"dragonpassCard\":\"CD7REO8V7B1R\",\"ticketNo\":\"7844032018822\",\"arrAirport\":\"CAN\",\"expireDate\":1620752488512,\"id\":\"22105110000139-2\",\"pushTime\":1620752470736,\"goodsName\":\"北京大兴机场-专车券（国内）\",\"subGoodsName\":\"10元专车券（需提前30分钟约车）\",\"deptAirport\":\"PVG\",\"orderNo\":\"22105110000139\",\"dragonpassCardStatus\":\"3\",\"flightKey\":\"CZ3549-20210512-PVG-CAN\",\"marketingId\":\"TESTLHMXLQZ00\",\"flightNo\":\"CZ3549\",\"subGoodsId\":5621,\"segOrder\":\"2\",\"deptDate\":1620748800000},{\"passengerName\":\"\",\"deptTime\":\"06:55\",\"goodsId\":\"D2002250000001\",\"idCard\":\"110101200101010157\",\"carbin\":\"H\",\"dragonpassCardUpdateTime\":\"2021-05-11 14:10:06.0\",\"xType\":\"HOTELCOUPON\",\"compareKey\":1620713406000,\"dragonpassCard\":\"OGDVR8L5DPWL\",\"ticketNo\":\"7844032018822\",\"arrAirport\":\"CAN\",\"expireDate\":1620713425593,\"id\":\"22105110000142-2\",\"pushTime\":1620713407934,\"goodsName\":\"货架专用-北京大兴机场-酒店券（国际）\",\"subGoodsName\":\"1元酒店券\",\"deptAirport\":\"PVG\",\"orderNo\":\"22105110000142\",\"dragonpassCardStatus\":\"0\",\"flightKey\":\"CZ3549-20210512-PVG-CAN\",\"marketingId\":\"TESTLHLQ00\",\"flightNo\":\"CZ3549\",\"subGoodsId\":5961,\"segOrder\":\"2\",\"deptDate\":1620748800000}]},\"relationKey\":[\"TICKET-7844032018822\",\"FLN-CZ-3549\",\"ORG-PVG\",\"DST-CAN\"],\"pnrNo\":\"MJ2HKR\",\"index\":202105121620782060,\"type\":\"ET\",\"objectId\":\"B2C_PRIMARY_ETS_7844032018822_2\"}],\"errorCode\":\"0\",\"errorMsg\":\"成功\",\"finish\":true,\"success\":true}\n";
//        JSONObject resultJson = JSONObject.parseObject(result);
//        List<FlightWithXOrder> flightWithXOrderList = new ArrayList<>();
//        if (resultJson.getString("success").equals("true")) {
//            JSONArray data = resultJson.getJSONArray("data");
//            if (data != null && data.size() > 0) {
//                for (int i = 0; i < data.size(); i++) {
//                    JSONObject jsonObject = data.getJSONObject(i);
//                    String type = jsonObject.getString("type");
//                    if (type.equals("ET")) {
//                        JSONObject innerData = jsonObject.getJSONObject("innerData");
//                        JSONArray xOrdersJsonArray = innerData.getJSONArray("xOrders");
//                        if (!CollectionUtils.isEmpty(xOrdersJsonArray)) {
//                            for (int j = 0; j < xOrdersJsonArray.size(); j++) {
//                                FlightWithXOrder flightWithXOrder = new FlightWithXOrder();
//                                String flightJson = innerData.getJSONObject("ticket").getJSONArray("coupons").getJSONObject(0).getString("flight");
//                                Flight flight = JSON.parseObject(flightJson, Flight.class);
//                                String xOrderJson = xOrdersJsonArray.getString(j);
//                                XOrder xOrder = JSON.parseObject(xOrderJson, XOrder.class);
//                                flightWithXOrder.setFlight(flight);
//                                flightWithXOrder.setXOrder(xOrder);
//                                flightWithXOrderList.add(flightWithXOrder);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        System.out.println(JSON.toJSONString(flightWithXOrderList));
//    }


//    public static void main(String[] args) {
//        String content = "[{\"passengerInfo\":{\"birthDate\":\"2001-01-01\",\"gender\":\"\",\"idcard\":\"110101200101010018\",\"idtype\":\"NI\",\"mobilephone\":\"\",\"pkgId\":\"\",\"psgType\":\"0\",\"psgname\":\"雷霆嘎巴\"},\"pushTime\":1621303643424,\"ticketCoupon\":[{\"arrAirport\":\"CSX\",\"arrTime\":\"0930\",\"baggage\":\"20K\",\"cabin\":\"L\",\"carrier\":\"CZ\",\"coupon\":\"1\",\"depAirport\":\"CAN\",\"depTime\":\"0815\",\"discountAmount\":\"\",\"discountcode\":\"\",\"farebasis\":\"LYXHAAMD\",\"flightDate\":\"2021-05-19\",\"flightno\":\"8543\",\"insuranceAmount\":\"\",\"insuranceCompany\":\"\",\"insuranceNo\":\"\",\"insuranceProduct\":\"\",\"planeType\":\"32G\",\"segmentLable\":\"\",\"segorder\":\"1\",\"xOrderNo\":\"22105180000023\"}],\"ticketOrder\":{\"bizfee\":\"0\",\"bizfeeRemark\":\"航意保险费\",\"bookAgent\":\"164184\",\"bookAgentName\":\"触屏版销售\",\"bookUser\":\"085532231974\",\"campaignScriptId\":50,\"contact\":\"雷霆嘎巴|15900010010|15900010010|\",\"createDate\":\"2021-05-18 10:33:47.0\",\"domesticindicate\":\"1\",\"groupName\":\"\",\"isGroup\":\"0\",\"mileage\":\"\",\"orderStatus\":\"T\",\"orderno\":\"C2105180000090\",\"originalOrderNo\":\"\",\"payDate\":\"210518\",\"payStatus\":\"P\",\"paybank\":\"ECARD\",\"pnrno\":\"NLY8J6\",\"refOrderno\":\"\",\"refTicketno\":\"\",\"reveivablePrice\":\"\",\"serviceNo\":\"\",\"ticketTime\":\"2021-05-18 10:33:53.0\",\"totalCost\":\"1000\",\"totalDiscount\":\"0\",\"totalLuggageCharge\":\"0\",\"totalOrderMoney\":\"1140.0\",\"totalPayMoney\":\"1140\",\"totalTax\":\"\",\"uppbillno\":\"ECARD904210518071808\"},\"ticketno\":\"7844032020259\"},{\"passengerInfo\":{\"birthDate\":\"2005-04-20\",\"gender\":\"\",\"idcard\":\"123123\",\"idtype\":\"PP\",\"mobilephone\":\"\",\"pkgId\":\"\",\"psgType\":\"0\",\"psgname\":\"LEITING/GUNGUN\"},\"pushTime\":1621303643424,\"ticketCoupon\":[{\"arrAirport\":\"CSX\",\"arrTime\":\"0930\",\"baggage\":\"20K\",\"cabin\":\"L\",\"carrier\":\"CZ\",\"coupon\":\"1\",\"depAirport\":\"CAN\",\"depTime\":\"0815\",\"discountAmount\":\"\",\"discountcode\":\"\",\"farebasis\":\"LYXHAAMD\",\"flightDate\":\"2021-05-19\",\"flightno\":\"8543\",\"insuranceAmount\":\"\",\"insuranceCompany\":\"\",\"insuranceNo\":\"\",\"insuranceProduct\":\"\",\"planeType\":\"32G\",\"segmentLable\":\"\",\"segorder\":\"1\",\"xOrderNo\":\"22105180000023\"}],\"ticketOrder\":{\"bizfee\":\"0\",\"bizfeeRemark\":\"航意保险费\",\"bookAgent\":\"164184\",\"bookAgentName\":\"触屏版销售\",\"bookUser\":\"085532231974\",\"campaignScriptId\":50,\"contact\":\"雷霆嘎巴|15900010010|15900010010|\",\"createDate\":\"2021-05-18 10:33:47.0\",\"domesticindicate\":\"1\",\"groupName\":\"\",\"isGroup\":\"0\",\"mileage\":\"\",\"orderStatus\":\"T\",\"orderno\":\"C2105180000090\",\"originalOrderNo\":\"\",\"payDate\":\"210518\",\"payStatus\":\"P\",\"paybank\":\"ECARD\",\"pnrno\":\"NLY8J6\",\"refOrderno\":\"\",\"refTicketno\":\"\",\"reveivablePrice\":\"\",\"serviceNo\":\"\",\"ticketTime\":\"2021-05-18 10:33:53.0\",\"totalCost\":\"1000\",\"totalDiscount\":\"0\",\"totalLuggageCharge\":\"0\",\"totalOrderMoney\":\"1140.0\",\"totalPayMoney\":\"1140\",\"totalTax\":\"\",\"uppbillno\":\"ECARD904210518071808\"},\"ticketno\":\"7844032020258\"}]";
//        JSONArray jsonArray = JSONArray.parseArray(content);
//        System.out.println("实时同步MQ收到消息" + jsonArray);
//        if (!CollectionUtils.isEmpty(jsonArray)) {
//            for (int i = 0; i < jsonArray.size(); i++) {
//                com.alibaba.fastjson.JSONObject json = jsonArray.getJSONObject(i);
//                String totalPayMoney = json.getJSONObject("ticketOrder").getString("totalPayMoney");
//                JSONArray ticketCoupon = json.getJSONArray("ticketCoupon");
//                String xOrderno = ticketCoupon.getJSONObject(0).getString("xOrderNo");
//                String ticketNo = json.getString("ticketno");
//                long pushTime = json.getLong("pushTime");
//                String psgCertNo = json.getJSONObject("passengerInfo").getString("idcard");
//                System.out.println(totalPayMoney + " " + pushTime + " " + ticketNo + " " + xOrderno + " " + psgCertNo);
//            }
//        }
//    }


//    public static void main(String[] args) {
//        String content="[\n" +
//                "    {\n" +
//                "        \"emdCouponInfo\": [\n" +
//                "            {\n" +
//                "                \"couponNo\": \"1\",\n" +
//                "                \"emdCouponNo\": \"1\",\n" +
//                "                \"emdNo\": \"7847841231639\",\n" +
//                "                \"segOrder\": \"1\",\n" +
//                "                \"ticketNo\": \"7844030025530\"\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"emdNo\": \"7847841231639\",\n" +
//                "        \"emdTicketCoupon\": {\n" +
//                "            \"arrAirport\": \"PKX\",\n" +
//                "            \"arrTime\": \"11:45\",\n" +
//                "            \"cabin\": \"\",\n" +
//                "            \"depAirport\": \"CAN\",\n" +
//                "            \"depTime\": \"08:30\",\n" +
//                "            \"ext\": \"\",\n" +
//                "            \"flightDate\": \"2021-04-29 00:00:00.0\",\n" +
//                "            \"flightNo\": \"3149\",\n" +
//                "            \"plane\": \"\",\n" +
//                "            \"remark\": \"南航电子商务处\",\n" +
//                "            \"segOrder\": \"1\",\n" +
//                "            \"status\": \"1\",\n" +
//                "            \"typeName\": \"泊车券\"\n" +
//                "        },\n" +
//                "        \"passengerInfo\": {\n" +
//                "            \"email\": \"\",\n" +
//                "            \"fpcardno\": \"\",\n" +
//                "            \"idCard\": \"ASD1112\",\n" +
//                "            \"idType\": \"PP\",\n" +
//                "            \"phone\": \"13619895159\",\n" +
//                "            \"pnrno\": \"\",\n" +
//                "            \"psgName\": \"ZHANG/TEST\",\n" +
//                "            \"psgType\": \"0\"\n" +
//                "        },\n" +
//                "        \"pushTime\": 1618454640545,\n" +
//                "        \"seatTicketNo\": \"7844030025530\",\n" +
//                "        \"serviceOrder\": {\n" +
//                "            \"bookAgentName\": \"厦门市利航客货代理服务有限公司\",\n" +
//                "            \"channel\": \"B2B\",\n" +
//                "            \"city\": \"广州\",\n" +
//                "            \"currency\": \"CNY\",\n" +
//                "            \"deductionFee\": \"0\",\n" +
//                "            \"emdGlobal\": {\n" +
//                "                \"bankGatewayName\": \"易宝信用卡无卡支付\",\n" +
//                "                \"billno\": \"YEPI900210415033365\",\n" +
//                "                \"currency\": \"CNY\",\n" +
//                "                \"globalno\": \"GO2104150000057\",\n" +
//                "                \"payMode\": \"在线支付\",\n" +
//                "                \"payStatus\": \"\",\n" +
//                "                \"paydate\": \"2021-04-15 10:43:53.0\",\n" +
//                "                \"totalPayment\": \"10\"\n" +
//                "            },\n" +
//                "            \"groupName\": \"\",\n" +
//                "            \"isGroup\": \"\",\n" +
//                "            \"national\": \"国内\",\n" +
//                "            \"ncOrderNo\": \"22104150000010\",\n" +
//                "            \"orderno\": \"B2104154999999\",\n" +
//                "            \"payMoney\": \"10\",\n" +
//                "            \"saleOUName\": \"厦门市利航客货代理服务有限公司\",\n" +
//                "            \"saleOUType\": \"南航直属售票处\",\n" +
//                "            \"seatOrderno\": \"SO2104150000057\",\n" +
//                "            \"soStatus\": \"E\",\n" +
//                "            \"submitMobilePhone\": \"\",\n" +
//                "            \"submitPhone\": \"13619895159\",\n" +
//                "            \"submitusername\": \"CAN0001\",\n" +
//                "            \"ticketTime\": \"2021-04-15 10:44:00.0\",\n" +
//                "            \"totalCost\": \"10\",\n" +
//                "            \"totalTax\": \"0\",\n" +
//                "            \"userId\": \"CAN0001\"\n" +
//                "        }\n" +
//                "    }\n" +
//                "]";
//        JSONArray jsonArray = JSON.parseArray(content);
//        if (!CollectionUtils.isEmpty(jsonArray)){
//            for (int i = 0; i < jsonArray.size(); i++) {
//                JSONObject json = jsonArray.getJSONObject(i);
//                String xOrderNo = json.getJSONObject("serviceOrder").getString("ncOrderNo");
//                String ticketNo = json.getString("seatTicketNo");
//                Long pushTime = json.getLong("pushTime");
//                String idCard = json.getJSONObject("passengerInfo").getString("idCard");
//                System.out.println(xOrderNo+" "+ticketNo+" "+pushTime+ " "+idCard);
//
//            }
//        }
//    }

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @PostMapping("/readExcel")
//    public void readExcel(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        //封装读取的数据
//        List<OrderExcel> list = new ArrayList<>();
//
//        List<OrderExcel> returnList = new ArrayList<>();
//
//        if (file == null) {
//            logger.info("文件不存在");
//            throw new FileNotFoundException("文件不存在");
//        }
//        java.lang.String fileName = file.getOriginalFilename();
//        if (!Objects.requireNonNull(fileName).endsWith("xls") && !fileName.endsWith("xlsx")) {
//            logger.info(fileName + "不是excle文件");
//            throw new IOException(fileName + "不是excle文件");
//        }
//        InputStream is = file.getInputStream();
//        Workbook workbook = null;
//        //根据文件后缀名不同(xls和xlsx)获得不同的Workbook工作簿类对象
//        if (fileName.endsWith("xls")) {
//            //2003
//            workbook = new HSSFWorkbook(is);
//        } else if (fileName.endsWith("xlsx")) {
//            //2007
//            workbook = new XSSFWorkbook(is);
//        }
//        if (workbook != null) {
//            Sheet sheet = workbook.getSheetAt(0);
//            if (sheet != null) {
//                int firstRowNum = sheet.getFirstRowNum();//开始行
//                int lastRowNum = sheet.getLastRowNum(); //结束行
//
//                //不读取第一行
//                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
//                    Row row = sheet.getRow(rowNum);
//                    if (row == null) {
//                        continue;
//                    }
//                    OrderExcel orderExcel = new OrderExcel();
//                    orderExcel.setOrderNo(getCellValue(row.getCell(0)));
//                    orderExcel.setSoOrdersDetail(getCellValue(row.getCell(1)));
//                    list.add(orderExcel);
//
//                }
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                if (!CollectionUtils.isEmpty(list)) {
//                    for (OrderExcel orderExcel : list) {
//                        String orderNo = orderExcel.getOrderNo();
//                        String soOrdersDetail = orderExcel.getSoOrdersDetail();
//                        SoOrder soOrder = JSON.parseObject(soOrdersDetail, SoOrder.class);
//                        if (soOrder.getOperator()!=null){
//                            Operator operator = soOrder.getOperator();
//                            String channelId = operator.getChannelId();
//                            if (StringUtils.isEmpty(channelId)){
//                                operator.setChannelId("daxing");
//                                soOrder.setOperator(operator);
//                            }
//                        }
//                        String format = sdf.format(new Date());
//                        OrderParams orderParams = soOrder.getOrderParams();
//                        GlobalOrder globalOrder = orderParams.getGlobalOrder();
//                        globalOrder.setCreateTime(format);
//
//                        List<ServiceOrder> serviceOrders = globalOrder.getServiceOrders();
//                        List<ServiceOrder> newServiceOrders = new ArrayList<>();
//                        if (!CollectionUtils.isEmpty(serviceOrders)){
//                            for (ServiceOrder serviceOrder : serviceOrders) {
//                                serviceOrder.setCreateTime(format);
//                                Segment segment = serviceOrder.getSegments().get(0);
//                                if (StringUtils.isEmpty(segment.getCarrier())){
//                                    segment.setCarrier(segment.getFlightNo().substring(0,2));
//                                    segment.setFlightNo(segment.getFlightNo().substring(2));
//                                    serviceOrder.setSegments(Arrays.asList(segment));
//                                }
//
//                                newServiceOrders.add(serviceOrder);
//                            }
//                            globalOrder.setServiceOrders(newServiceOrders);
//                            orderParams.setGlobalOrder(globalOrder);
//                            soOrder.setOrderParams(orderParams);
//                        }
//                        System.out.println(JSON.toJSONString(soOrder));
//
////						int update = jdbcTemplate.update("update orders set so_orders_detail= ? where orderno=?", new Object[]{JSON.toJSONString(soOrder), orderNo});
////						System.out.println(update);
//                        OrderExcel excel = new OrderExcel();
//                        excel.setOrderNo(orderNo);
//                        excel.setSoOrdersDetail("update orders set so_orders_detail= '" + JSON.toJSONString(soOrder)+"' where orderno= '" +orderNo+"'");
//                        returnList.add(excel);
//                    }
//
//                    Map<String, List<String>> map = new HashMap<String, List<String>>();
//                    for (int i = 0; i < returnList.size(); i++) {
//                        ArrayList<String> members = new ArrayList<String>();
//                        members.add(returnList.get(i).getOrderNo() + "");
//                        members.add(returnList.get(i).getSoOrdersDetail());
//                        map.put((i+1) + "", members);
//                    }
//                    String[] stringArray={"orderNo","json"};
//                    ExcelUtil.createExcel(map, stringArray);
//
////					if (!CollectionUtils.isEmpty(returnList)){
////						//1.在内存中创建一个excel文件
////						HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
////						//2.创建工作簿
////						HSSFSheet sheet1 = hssfWorkbook.createSheet();
////						//3.创建标题行
////						HSSFRow titleRow = sheet1.createRow(0);
////						titleRow.createCell(0).setCellValue("orderno");
////						titleRow.createCell(1).setCellValue("json");
////						for (OrderExcel orderExcel : returnList) {
////							//获取最后一行的行号
////							int lastRowNum1 = sheet1.getLastRowNum();
////							HSSFRow sheetRow = sheet1.createRow(lastRowNum1 + 1);
////							sheetRow.createCell(0).setCellValue(orderExcel.getOrderNo());
////							sheetRow.createCell(1).setCellValue(orderExcel.getSoOrdersDetail());
////						}
////
////						//创建文件名
////						String resultFileName ="daxingexcel";
////						ServletOutputStream outputStream = null;
////						try {
////							outputStream = response.getOutputStream();
////							response.setContentType("application/vnd.ms-excel;charset=utf-8");
////							response.setHeader("Content-Disposition", "attachment;filename=" + new String(resultFileName.getBytes("gbk"), "iso8859-1") + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls");
////							hssfWorkbook.write(outputStream);
////							outputStream.flush();
////						} catch (IOException e) {
////							e.printStackTrace();
////						} finally {
////							try {
////								if (outputStream != null) {
////									outputStream.close();
////								}
////							} catch (IOException e) {
////								e.printStackTrace();
////							}
////						}
////					}
//                }
//            }
//        }
//    }


//    private static String getCellValue(Cell cell) {
//        String cellValue = "";
//        if (cell == null) {
//            return cellValue;
//        }
//        //把数字当成String来读，避免出现1读成1.0的情况
//        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//            cell.setCellType(Cell.CELL_TYPE_STRING);
//        }
//        //判断数据的类型
//        switch (cell.getCellType()) {
//            case Cell.CELL_TYPE_NUMERIC: //数字
//                cellValue = String.valueOf(cell.getNumericCellValue());
//                break;
//            case Cell.CELL_TYPE_STRING: //字符串
//                cellValue = String.valueOf(cell.getStringCellValue());
//                break;
//            case Cell.CELL_TYPE_BOOLEAN: //Boolean
//                cellValue = String.valueOf(cell.getBooleanCellValue());
//                break;
//            case Cell.CELL_TYPE_FORMULA: //公式
//                cellValue = String.valueOf(cell.getCellFormula());
//                break;
//            case Cell.CELL_TYPE_BLANK: //空值
//                cellValue = "";
//                break;
//            case Cell.CELL_TYPE_ERROR: //故障
//                cellValue = "非法字符";
//                break;
//            default:
//                cellValue = "未知类型";
//                break;
//        }
//        return cellValue;
//    }


//    public static void main(String[] args) {
//        String json="{\"operator\":{\"channelId\":\"B2COU\",\"opId\":\"441686063606\",\"opName\":\"441686063606\",\"orgId\":\"B2COU\",\"orgName\":\"B2C网上销售散客\"},\"orderParams\":{\"globalOrder\":{\"amount\":\"28\",\"costPrice\":\"28\",\"createTime\":\"2020-07-29 09:27:28\",\"discount\":\"0\",\"domesticindicate\":\"0\",\"equivBsrCurrency\":\"CNY\",\"equivFare\":\"28\",\"equivFareCurrency\":\"CNY\",\"income\":\"28\",\"issueType\":\"0\",\"ocpNo\":\"GO2007293000021\",\"originalBsrCurrency\":\"CNY\",\"payMiles\":\"0\",\"payMoney\":\"28\",\"serviceOrders\":[{\"amount\":\"10\",\"costPrice\":\"10\",\"createTime\":\"2020-07-29 09:27:27\",\"discount\":\"0\",\"domesticindicate\":\"0\",\"income\":\"10\",\"issueType\":\"0\",\"ncOrderNo\":\"22007290000006\",\"passenger\":{\"idCard\":\"1234567\",\"idType\":\"PP\",\"phone\":\"13100002222\",\"psgName\":\"NIU/TESTER\",\"type\":\"0\"},\"payMiles\":\"0\",\"payMoney\":\"10\",\"phone\":\"13100002222\",\"segments\":[{\"arrAirport\":\"HND\",\"arrTime\":\"19:40\",\"carrier\":\"CZ\",\"depAirport\":\"CAN\",\"depTime\":\"15:05\",\"flightDate\":\"2020-12-22\",\"flightNo\":\"3085\",\"realSegOrder\":\"CAN-HND\",\"segOrder\":\"1\"}],\"tickets\":[{\"amount\":\"10\",\"costPrice\":\"10\",\"discount\":\"0\",\"equivFare\":\"10\",\"ext1\":\"客服中心&#40;备用1&#41;\",\"ext2\":\"100\",\"income\":\"10\",\"issueEmd\":\"1\",\"payMiles\":\"0\",\"payMoney\":\"10\",\"segOrder\":\"1\"}],\"type\":\"109\"},{\"amount\":\"10\",\"costPrice\":\"10\",\"createTime\":\"2020-07-29 09:27:27\",\"discount\":\"0\",\"domesticindicate\":\"0\",\"income\":\"10\",\"issueType\":\"0\",\"ncOrderNo\":\"22007290000005\",\"passenger\":{\"idCard\":\"1234567\",\"idType\":\"PP\",\"phone\":\"13100002222\",\"psgName\":\"NIU/TESTER\",\"type\":\"0\"},\"payMiles\":\"0\",\"payMoney\":\"10\",\"phone\":\"13100002222\",\"segments\":[{\"arrAirport\":\"HND\",\"arrTime\":\"19:40\",\"carrier\":\"CZ\",\"depAirport\":\"CAN\",\"depTime\":\"15:05\",\"flightDate\":\"2020-12-22\",\"flightNo\":\"3085\",\"realSegOrder\":\"CAN-HND\",\"segOrder\":\"1\"}],\"tickets\":[{\"amount\":\"10\",\"costPrice\":\"10\",\"discount\":\"0\",\"equivFare\":\"10\",\"ext1\":\"客服中心&#40;备用1&#41;\",\"ext2\":\"100\",\"income\":\"10\",\"issueEmd\":\"1\",\"payMiles\":\"0\",\"payMoney\":\"10\",\"segOrder\":\"1\"}],\"type\":\"105\"},{\"amount\":\"1\",\"costPrice\":\"1\",\"createTime\":\"2020-07-29 09:27:27\",\"discount\":\"0\",\"domesticindicate\":\"0\",\"income\":\"1\",\"issueType\":\"0\",\"ncOrderNo\":\"22007290000004\",\"passenger\":{\"idCard\":\"1234567\",\"idType\":\"PP\",\"phone\":\"13100002222\",\"psgName\":\"NIU/TESTER\",\"type\":\"0\"},\"payMiles\":\"0\",\"payMoney\":\"1\",\"phone\":\"13100002222\",\"segments\":[{\"arrAirport\":\"HND\",\"arrTime\":\"19:40\",\"carrier\":\"CZ\",\"depAirport\":\"CAN\",\"depTime\":\"15:05\",\"flightDate\":\"2020-12-22\",\"flightNo\":\"3085\",\"realSegOrder\":\"CAN-HND\",\"segOrder\":\"1\"}],\"tickets\":[{\"amount\":\"1\",\"costPrice\":\"1\",\"discount\":\"0\",\"equivFare\":\"1\",\"ext1\":\"\",\"ext2\":\"100\",\"income\":\"1\",\"issueEmd\":\"1\",\"payMiles\":\"0\",\"payMoney\":\"1\",\"segOrder\":\"1\"}],\"type\":\"113\"},{\"amount\":\"28\",\"costPrice\":\"28\",\"createTime\":\"2020-07-29 09:27:28\",\"discount\":\"0\",\"domesticindicate\":\"0\",\"income\":\"28\",\"issueType\":\"0\",\"ncOrderNo\":\"22007290000003\",\"passenger\":{\"idCard\":\"1234567\",\"idType\":\"PP\",\"phone\":\"13100002222\",\"psgName\":\"NIU/TESTER\",\"type\":\"0\"},\"payMiles\":\"0\",\"payMoney\":\"28\",\"phone\":\"13100002222\",\"segments\":[{\"arrAirport\":\"HND\",\"arrTime\":\"19:40\",\"carrier\":\"CZ\",\"depAirport\":\"CAN\",\"depTime\":\"15:05\",\"flightDate\":\"2020-12-22\",\"flightNo\":\"3085\",\"realSegOrder\":\"CAN-HND\",\"segOrder\":\"1\"}],\"tickets\":[{\"amount\":\"28\",\"costPrice\":\"28\",\"discount\":\"0\",\"equivFare\":\"28\",\"ext1\":\"北京民航机场巴士有限公司（大巴票）\",\"ext2\":\"100\",\"income\":\"28\",\"issueEmd\":\"1\",\"payMiles\":\"0\",\"payMoney\":\"28\",\"segOrder\":\"1\"}],\"type\":\"103\"}]},\"payments\":[{\"bankAckTime\":\"2020-07-29 09:27:27\",\"bankGatewayCode\":\"ALIC\",\"billDate\":\"2020-07-29 09:27:27\",\"billNo\":\"ALIC9012007293451215\",\"currency\":\"CNY\",\"ncOrderNo\":\"22007290000006\",\"payDate\":\"2020-07-29 09:27:27\",\"payMiles\":\"0\",\"payMoney\":\"10\",\"type\":\"I\",\"uppBillNo\":\"ALIC901200729345121\"},{\"bankAckTime\":\"2020-07-29 09:27:27\",\"bankGatewayCode\":\"ALIC\",\"billDate\":\"2020-07-29 09:27:27\",\"billNo\":\"ALIC9012007293451214\",\"currency\":\"CNY\",\"ncOrderNo\":\"22007290000005\",\"payDate\":\"2020-07-29 09:27:27\",\"payMiles\":\"0\",\"payMoney\":\"10\",\"type\":\"I\",\"uppBillNo\":\"ALIC901200729345121\"},{\"bankAckTime\":\"2020-07-29 09:27:27\",\"bankGatewayCode\":\"ALIC\",\"billDate\":\"2020-07-29 09:27:27\",\"billNo\":\"ALIC9012007293451213\",\"currency\":\"CNY\",\"ncOrderNo\":\"22007290000004\",\"payDate\":\"2020-07-29 09:27:27\",\"payMiles\":\"0\",\"payMoney\":\"1\",\"type\":\"I\",\"uppBillNo\":\"ALIC901200729345121\"},{\"bankAckTime\":\"2020-07-29 09:27:27\",\"bankGatewayCode\":\"ALIC\",\"billDate\":\"2020-07-29 09:27:27\",\"billNo\":\"ALIC9012007293451212\",\"currency\":\"CNY\",\"ncOrderNo\":\"22007290000003\",\"payDate\":\"2020-07-29 09:27:27\",\"payMiles\":\"0\",\"payMoney\":\"28\",\"type\":\"I\",\"uppBillNo\":\"ALIC901200729345121\"}]},\"relatedOrderNo\":\"C2007294999980\",\"xOrderno\":\"22007290000003\"}\n";
//        String orderNo="22007290000003";
//        SoOrder soOrder = JSON.parseObject(json, SoOrder.class);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Operator operator = soOrder.getOperator();
//        String channelId = operator.getChannelId();
//        if (StringUtils.isEmpty(channelId)){
//            operator.setChannelId("daxing");
//            soOrder.setOperator(operator);
//        }
//        String format = sdf.format(new Date());
//        OrderParams orderParams = soOrder.getOrderParams();
//        GlobalOrder globalOrder = orderParams.getGlobalOrder();
//        globalOrder.setCreateTime(format);
//
//        List<ServiceOrder> serviceOrders = globalOrder.getServiceOrders();
//        List<ServiceOrder> newServiceOrders = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(serviceOrders)){
//            for (ServiceOrder serviceOrder : serviceOrders) {
//                serviceOrder.setCreateTime(format);
//                Segment segment = serviceOrder.getSegments().get(0);
//                if (StringUtils.isEmpty(segment.getCarrier())){
//                    segment.setCarrier(segment.getFlightNo().substring(0,2));
//                    segment.setFlightNo(segment.getFlightNo().substring(2));
//                    serviceOrder.setSegments(Arrays.asList(segment));
//                }
//
//                newServiceOrders.add(serviceOrder);
//            }
//            globalOrder.setServiceOrders(newServiceOrders);
//            orderParams.setGlobalOrder(globalOrder);
//            soOrder.setOrderParams(orderParams);
//        }
//        System.out.println("soOrder---------------->"+JSON.toJSONString(soOrder));
////		jdbcTemplate.update("update orders set so_orders_detail= ? where orderno=?",new Object[]{JSON.toJSONString(soOrder),orderNo});
//
//        System.out.println("hello");
//    }

    public static void main(String[] args) {
        Optional<String> optional = Optional.of("hello");
        optional.ifPresent(System.out::println);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateTimeFormatter.format(now));

        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeFormatter.format(now),dateTimeFormatter);
        System.out.println(localDateTime);

    }
}
