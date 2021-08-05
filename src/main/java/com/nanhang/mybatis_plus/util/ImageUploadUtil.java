package com.nanhang.mybatis_plus.util;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图片上传工具类
 */
public class ImageUploadUtil {

    public static String uploadFile(@RequestParam("file") MultipartFile file, String strPath) throws IOException {
        String fileName = file.getOriginalFilename();//获取文件名
        fileName = getFileName(fileName);
        String filepath = getUploadPath(strPath);
        if (!file.isEmpty()) {
            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(filepath + File.separator + fileName)))) {
                out.write(file.getBytes());
                out.flush();
                return "/" + strPath + "/" + fileName;
            } catch (IOException e) {
                return "error";//( "上传文件失败 IOException：" + e.getMessage());
            }
        } else {
            return "error";//( "上传文件失败，文件为空");
        }
    }

    /**
     * 文件名后缀前添加一个时间戳
     */
    private static String getFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        final SimpleDateFormat sDateFormate = new SimpleDateFormat("yyyymmddHHmmss");  //设置时间格式
        String nowTimeStr = sDateFormate.format(new Date()); // 当前时间
        fileName = fileName.substring(0, index) + "_" + nowTimeStr + fileName.substring(index);
        return fileName;
    }

    /**
     * 获取当前系统路径
     */
    private static String getUploadPath(String strPath) throws IOException {

        File file = new File(strPath);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        if (!file.exists()) file.mkdirs();

        return file.getAbsolutePath();

    }
}
