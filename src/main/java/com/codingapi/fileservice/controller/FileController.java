package com.codingapi.fileservice.controller;


import com.codingapi.fileservice.service.FileService;
import com.lorne.core.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileController  {


	@Autowired
	private FileService fileService;



	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		return "index";
	}



	/**
	 * 批量，单个  文件上传
	 * @param request
	 * @param response
     */
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public List<Map<String,Object>> uploadFile(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
		MultipartHttpServletRequest multipartRequest  = (MultipartHttpServletRequest) request;
		return fileService.uploadFile(multipartRequest);
	}


	/**
	 * 下载 文件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response  ) throws IOException, ServiceException {
		// 文件地址
		String path = ServletRequestUtils.getStringParameter(request, "path", "");
		fileService.downloadFile(path , response);
	}







}
