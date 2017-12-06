package com.codingapi.fileservice.service.impl;

import com.codingapi.fileservice.FileUrl;
import com.codingapi.fileservice.service.FileService;
import com.codingapi.fileservice.utils.FileUtils;
import com.lorne.core.framework.exception.ServiceException;
import com.lorne.core.framework.model.Msg;
import com.lorne.core.framework.utils.encode.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.FileChannel;
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




    /**
     * 文件 分割上传
     * @throws IOException
     */
    @Override
    public String bigUpload(String fileName, Integer total, String uuid, String md5, Integer blockNum , MultipartFile file) {
        try {
            String savePath = FileUrl.bigUploadFileInterimPath + uuid;
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File filePart = new File(saveDir, uuid + "_" + blockNum);
            if (filePart.exists()) {
                String fileMd5 = MD5Util.md5(filePart);
                if (fileMd5.equals(md5)) {
                    return "SUCCESS";
                } else {
                    filePart.delete();
                }
            }
            BufferedOutputStream buff = null;
            buff = new BufferedOutputStream(new FileOutputStream(filePart));
            buff.write(file.getBytes());
            buff.flush();
            buff.close();
            String fileMd5 = MD5Util.md5(filePart);
            if (!fileMd5.equals(md5))
                return "FAIL";
            if (saveDir.isDirectory()) {
                File[] fileArray = saveDir.listFiles();
                if (fileArray != null) {
                    if (fileArray.length == total) {
                        //分块全部上传完毕,合
                        File outputFile = new File(FileUrl.bigUploadFilePath, fileName);
                        FileChannel outChannel = new FileOutputStream(outputFile).getChannel();
                        //合并
                        FileChannel inChannel;
                        for (int i = 0; i < total; i++) {
                            File f = new File(saveDir, uuid+"_"+i);
                            inChannel = new FileInputStream(f).getChannel();
                            inChannel.transferTo(0, inChannel.size(), outChannel);
                            inChannel.close();
                            System.out.println("append: " + f.getName());
                            //删除分片
                            if (!f.delete()) {
                                System.out.println("删除失败");
                            }
                        }
                        outChannel.close();
                    }
                }
            }
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }


}
