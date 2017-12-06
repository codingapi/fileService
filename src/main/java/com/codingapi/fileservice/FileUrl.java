package com.codingapi.fileservice;

import com.lorne.core.framework.utils.config.ConfigHelper;

/**
 * Created by houcunlu on 2017/10/25.
 */
public class FileUrl {

    // 图片上传路径
    public static String imageUploadFilePath = "";


    // 大文件临时存放地址
    public static String bigUploadFileInterimPath = "";


    // 大文件存放地址
    public static String bigUploadFilePath = "";

    static {

        ConfigHelper configurationHandle = new ConfigHelper("config.properties");
        imageUploadFilePath =  configurationHandle.getStringValue("imageUploadFilePath");
        bigUploadFileInterimPath =  configurationHandle.getStringValue("bigUploadFileInterimPath");
        bigUploadFilePath =  configurationHandle.getStringValue("bigUploadFilePath");
        if ("".equals(imageUploadFilePath)) {
            throw new RuntimeException("图片上传路径为空");
        }

        if ("".equals(bigUploadFileInterimPath)) {
            throw new RuntimeException("大文件临时存放地址为空!");
        }

        if ("".equals(bigUploadFilePath)) {
            throw new RuntimeException("大文件存放地址为空!");
        }
    }

}
