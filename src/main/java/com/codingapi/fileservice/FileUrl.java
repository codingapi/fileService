package com.codingapi.fileservice;

import com.lorne.core.framework.utils.config.ConfigHelper;

/**
 * Created by houcunlu on 2017/10/25.
 */
public class FileUrl {

    // 图片上传路径
    public static String imageUploadFilePath = "";


    static {

        ConfigHelper configurationHandle = new ConfigHelper("config.properties");
        imageUploadFilePath =  configurationHandle.getStringValue("imageUploadFilePath");
        if ("".equals(imageUploadFilePath)) {
            throw new RuntimeException("图片上传路径为空");
        }
    }

}
