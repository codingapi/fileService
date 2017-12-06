package com.codingapi.fileservice.controller;

import com.codingapi.fileservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by houcunlu on 2017/11/20.
 */
@Controller
@RequestMapping("/bigFile")
public class BigFileController {


    @Autowired
    private FileService fileService;


    @ResponseBody
    @PostMapping("/getUuid")
    public String getUuid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }


    /**
     * 文件 分割上传
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request, @RequestParam("data") MultipartFile file) throws IOException {

        String fileName = request.getParameter("fileName");
        Integer total = Integer.parseInt(request.getParameter("total"));
        String uuid = request.getParameter("uuid");
        String md5 = request.getParameter("md5");
        Integer blockNum = Integer.parseInt(request.getParameter("blockNum"));
        return fileService.bigUpload(fileName, total, uuid, md5, blockNum , file);
    }

}