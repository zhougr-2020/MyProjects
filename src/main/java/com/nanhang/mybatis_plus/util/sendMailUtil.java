package com.nanhang.mybatis_plus.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Properties;

/**
 * @author zzx
 * @Description: ${todo}
 * @date 2018/11/12 11:56
 */

public class sendMailUtil {

    /**
     *
     * @param filePath 发送excel文件的路径
     * @param eamilUrl 接受邮件邮箱地址
     * @param sendSubject 邮件标题
     * @return
     */
    public static String sendMessage(String filePath, String[] eamilUrl, String sendSubject){

        //发送邮件邮箱信息
        String myEmailSMTPHost = "smtp.163.com";
        String myEmailAccount = "zhougr@163.com";
        String myEmailPassword = "CULXTHBHSENNMPWG";
        String senderName = "发送人昵称"; //发送人昵称

//        String[] eamilUrl = new String[] {"1963942081@qq.com"};//接受邮件邮箱
//        String sendSubject = "邮件标题"; //邮件标题
        String sendContent = "邮件内容"; //邮件内容
//        String imgPath = request.getSession().getServletContext().getRealPath("/") + "mail\\img\\1.jpg";// 发送图片
//        String filePath = request.getSession().getServletContext().getRealPath("/") + "mail\\file\\123.docx"; //发送附件
//        String filePath = request.getSession().getServletContext().getRealPath("/") + "mail\\file\\2018-11-09\\73f02f55-0109-4518-b108-ee01c1df93af.xlsx"; //发送附件

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

        Session session = Session.getDefaultInstance(props);// 根据配置创建会话对象, 用于和邮件服务器交互
        session.setDebug(true);

        try {
            //发送复杂邮件
            MimeMessage message = createComplicatedMessage(session, myEmailAccount, senderName, eamilUrl, sendSubject, sendContent,null, filePath, sendSubject);
            Transport transport = session.getTransport();  //获取邮件传输对象
            transport.connect(myEmailAccount, myEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * @Author: zzx
     * @Description: 创建邮件,图片路径可以为空
     * @Date: 2018/11/8 14:33
     * @Param: sendMail：发送人的邮箱,senderName：发送人的昵称,receiveMail:接收邮件的邮箱，subject：邮件标题，msgContent:邮件内容,imgPath:图片路径,filePath:附件路径,sendSubject：附件名字
     * @Return
     */
    public static MimeMessage createComplicatedMessage(Session session,String sendMail, String senderName, String[] receiveMail,String subject,String msgContent, String imgPath, String filePath, String sendSubject) throws Exception{

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sendMail,senderName,"UTF-8"));
        Address[] addr = new Address[receiveMail.length];
        for(int i=0;i<addr.length;i++){
            addr[i] = new InternetAddress(receiveMail[i], "", "UTF-8");
        }
        message.addRecipients(Message.RecipientType.TO, addr);
        message.setSubject(subject);

        /*
         * 邮件内容的创建：图片和附件
         */

        //创建文本节点
        MimeBodyPart text = new MimeBodyPart();
        //将文本和图片节点结合
        MimeMultipart text_image = new MimeMultipart();

        if(imgPath != null) {
            //创建图片节点
            MimeBodyPart image = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource(imgPath));  //读取本地文件
            image.setDataHandler(dh);   //将数据添加到节点
            image.setContentID("image_id");// 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）
            //将图片包含到文本内容中
            text.setContent(msgContent + "<br/><img src='cid:image_id'/>", "text/html;charset=UTF-8");
            text_image.addBodyPart(text);
            text_image.addBodyPart(image);
            text_image.setSubType("related"); //关联关系 有内嵌资源要定义related
        } else {
            text.setContent(msgContent, "text/html;charset=UTF-8");
            text_image.addBodyPart(text);
        }

        //将混合节点封装成普通节点BodyPart,邮件最终由多个BodyPart组成
        MimeBodyPart text_image_body = new MimeBodyPart();
        text_image_body.setContent(text_image);

        //添加附件节点
        MimeBodyPart document = new MimeBodyPart();
        DataHandler dhdoc = new DataHandler(new FileDataSource(filePath)); //读取本地文档
        document.setDataHandler(dhdoc);   //将附件数据添加到节点
//        document.setFileName(MimeUtility.decodeText(dhdoc.getName())); //设置附件文件名
        document.setFileName(sendSubject + ".xlsx"); //设置附件文件名

        //设置文本和图片，附件的关系（混合大节点）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image_body);
        mm.addBodyPart(document);
        mm.setSubType("mixed");  //有附件资源要定义mixed

        //最终节点添加到邮件中
        message.setContent(mm);
        message.setSentDate(new Date());
        message.saveChanges();

        return message;
    }
}
