package com.nanhang.mybatis_plus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * @author: immortal
 * @CreateDate: 2020/12/16 14:15
 * @Description: 发送短信
 */
@Component
public class SendMessage {
    private Logger logger = LoggerFactory.getLogger(SendMessage.class);


    public String username = "admin";

    public String password;

    public String userkey;

    public String casurlSms;


    public boolean sendSMS(String content, String phone) {
        Calendar cal = Calendar.getInstance();
        Long timestamp = cal.getTimeInMillis();
//        String username = cd.casusername;
//        String password = cd.caspassword;
//        String userkey = cd.caskey;
        String sign = MD5_SUPPLIER.sign(content + phone + username + password + userkey + timestamp, "", "UTF-8");

        String xml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" + "<SOAP-ENV:Body>" + "<m:sendSMSData xmlns:m=\"http://Service.casInterFace.com/\">" + "<interfacelogin>" + "<password>" + password
                + "</password>" + "<sign>" + sign + "</sign>" + "<signType>md5</signType>" + "<timesiamp>" + timestamp + "</timesiamp>" + "<username>" + username + "</username>" + "</interfacelogin>" + "<mobileno>" + phone + "</mobileno>" + "<content><![CDATA[" + content + "]]></content>" + "</m:sendSMSData>" + "</SOAP-ENV:Body>" + "</SOAP-ENV:Envelope>";

        boolean flag = false;
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        InputStreamReader isr = null;
        try {
            URL url = new URL(casurlSms);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            out = conn.getOutputStream();
            out.write(xml.getBytes("utf-8"));
            if (conn.getResponseCode() == 200) {
                StringBuilder sb = new StringBuilder();
                in = conn.getInputStream();
                isr = new InputStreamReader(in, "UTF-8");
                char[] c = new char[1024];
                int a = isr.read(c);
                while (a != -1) {
                    sb.append(new String(c, 0, a));
                    a = isr.read(c);
                }
                Document doc = getDom(sb + "");
                flag = doc.getElementsByTagName("code").item(0).getTextContent().equals("0");
            }

        } catch (Exception e) {
            logger.error("util/PublicTool", e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (out != null) {
                    out.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                logger.error("util/PublicTool", e);
                e.printStackTrace();
            }
        }

        return flag;
    }

    /**
     * 返回一个dom
     *
     * @param xmlstr
     * @return
     */
    private Document getDom(String xmlstr) {// 返回一个dom
        Document doc = null;
        try {
            DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder dombuilder = domfac.newDocumentBuilder();
            doc = dombuilder.parse(new InputSource(new StringReader(xmlstr)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("util/PublicTool", e);
            e.printStackTrace();
        }
        return doc;
    }
}
