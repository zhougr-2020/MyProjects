package com.nanhang.mybatis_plus.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 创建excel，写入数据，然后下载到本地
 * 
 * @author baiyu
 *
 */
public class DownloadExcel {

    public static void main(String[] args) {
        Map<String, List<String>> memberMap = getMember();
        String[] strArray = excelTitle();
        ExcelUtil.createExcel(memberMap, strArray);
    }

    /**
     * 初始化数据，将会被存储到excel表格中
     * 
     * @return
     * @throws Exception
     */
    private static Map<String, List<String>> getMember() {
        List<Member> list = new ArrayList<Member>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        try {
            Member user1 = new Member(1, "熊大", 24, df.parse("1993-08-28"));
            Member user2 = new Member(2, "熊二", 23, df.parse("1994-08-19"));
            Member user3 = new Member(3, "熊熊", 24, df.parse("1983-11-22"));

            list.add(user1);
            list.add(user2);
            list.add(user3);

            for (int i = 0; i < list.size(); i++) {
                ArrayList<String> members = new ArrayList<String>();
                members.add(list.get(i).getCode() + "");
                members.add(list.get(i).getName());
                members.add(list.get(i).getAge() + "");
                members.add(df.format(list.get(i).getBirth()));
                map.put(list.get(i).getCode() + "", members);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 创建excel title
     */
    public static String[] excelTitle() {
        String[] strArray = { "学号", "姓名", "年龄", "生日" };
        return strArray;
    }
}
