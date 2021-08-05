package com.nanhang.mybatis_plus.controller;

import com.nanhang.mybatis_plus.annotation.LoginCheck;
import com.nanhang.mybatis_plus.pojo.User;
import com.nanhang.mybatis_plus.service.UserService;
import com.nanhang.mybatis_plus.style.factory.Result;
import com.nanhang.mybatis_plus.util.ImageUploadUtil;
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
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
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
    public Result check(@RequestBody @Valid User user) {
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

    @ApiOperation(value = "图片上传")
    @PostMapping("/upload")
    public Result upload(MultipartFile file, HttpServletRequest request) throws IOException {

//        if (file ==null){
//            return Result.error("file为空,上传失败");
//        }
//        String path = "";
//        String realPath = request.getSession().getServletContext().getRealPath("/");
//        path = realPath  + "static" + File.separator + "images" + File.separator;
//
//        File imagesFile = new File(path);
//        if (!imagesFile.exists()) {
//            imagesFile.mkdirs();
//        }
//        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
//        String fileName = UUID.randomUUID() + ".png";
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
//        byte[] bs = new byte[1024];
//        int len;
//        while ((len = fileInputStream.read(bs)) != -1) {
//            bos.write(bs, 0, len);
//        }
//        bos.flush();
//        bos.close();
        return Result.success(ImageUploadUtil.uploadFile(file,"static/images"));

    }

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


    public static void main(String[] args) {
//        Optional<String> optional = Optional.of("hello");
//        optional.ifPresent(System.out::println);
//
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        System.out.println(dateTimeFormatter.format(now));
//
//        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeFormatter.format(now), dateTimeFormatter);
//        System.out.println(localDateTime);
        int a =0;
        System.out.println(a+=5);
        System.out.println((a += 5) == 120);
//
    }
}
