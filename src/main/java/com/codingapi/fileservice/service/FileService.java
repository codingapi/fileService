package com.codingapi.fileservice.service;

import com.lorne.core.framework.exception.ServiceException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by houcunlu on 2017/10/25.
 */
public interface FileService {


    /**
     * 批量，单个  文件上传
     * @param
     * @param
     */
    List<Map<String,Object>> uploadFile(MultipartHttpServletRequest multipartRequest ) throws ServiceException;



    void downloadFile(String path, HttpServletResponse response) throws ServiceException, IOException;
}
