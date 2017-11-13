package com.fileservice.service.impl;

import com.fileservice.FileUrl;
import com.fileservice.service.FileService;
import com.fileservice.utils.FileUtils;
import com.lorne.core.framework.exception.ServiceException;
import com.lorne.core.framework.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by houcunlu on 2017/10/25.
 */
@Service
public class FileServiceImpl implements FileService {



    /**
     * 批量，单个  文件上传
     * @param
     * @param
     */
    @Override
    public List<Map<String, Object>> uploadFile(MultipartHttpServletRequest multipartRequest ) throws ServiceException {
        return  FileUtils.UploadFile(multipartRequest , FileUrl.imageUploadFilePath);
    }



    @Override
    public void downloadFile(String path, HttpServletResponse response) throws ServiceException, IOException {
        if(StringUtils.isEmpty(path)){
            throw new ServiceException("下载路径为空！");
        }
        String url =  FileUrl.imageUploadFilePath + path;
        File file = new File(url);
        if(!file.exists()){
            throw  new ServiceException("文件不存在！");
        }
        OutputStream outputStream = response.getOutputStream();
        org.apache.commons.io.FileUtils.copyFile(file, outputStream);

        response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");
    }


}
