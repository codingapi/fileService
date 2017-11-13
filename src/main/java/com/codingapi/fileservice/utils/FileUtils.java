package com.codingapi.fileservice.utils;

import com.lorne.core.framework.exception.ServiceException;
import com.lorne.core.framework.utils.DateUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class FileUtils {

	public static String getFileType(String path) {
		String name = "";
		try {
			if (path != null) {
				name = new File(path).getName();
			}
			if (!name.equals("") && name.contains(".")) {
				return name.split("\\.")[name.split("\\.").length - 1];
			}
			return "";
		} catch (Exception e) {
		}
		return name;
	}


	public static void saveInputStream2File(MultipartFile file, String path)
			throws Exception {
		createFile(path);
		org.apache.commons.io.FileUtils.copyInputStreamToFile(
				file.getInputStream(), new File(path));

	}

	public static void saveInputStream2File(InputStream inputStream, String path)
			throws Exception {
		createFile(path);
		org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, new File(path));

	}

	public static void createFile(String filePath) {
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 上传文件
	 * @param multipartRequest
	 * @param uploadFilePath
	 * @param
	 * @return
	 * @throws ServiceException
     */
	public static List<Map<String,Object>> UploadFile(MultipartHttpServletRequest multipartRequest , String uploadFilePath ) throws ServiceException {
		List<Map<String,Object>> fileStates = new ArrayList<Map<String,Object>>();
		if (multipartRequest == null) {
			throw  new ServiceException("获取文件失败！");
		} else {
			List<MultipartFile> files = multipartRequest.getFiles("file");
			if (files == null || files.size() <= 0) {
				throw  new ServiceException("未上传文件！");
			} else {
				try {
					String dayPath = DateUtil.formatDate(new Date(), "yyyyMMdd");
					for (MultipartFile file : files) {
						String name = file.getOriginalFilename();
						if(!name.equals("")){
							Map<String,Object> fileState = new HashMap<>();
							name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
							String timepath = String.format("%s%s",System.currentTimeMillis(),(int) (Math.random() * 99999));
							String upath = String.format("%s%s/%s/%s", uploadFilePath,
									dayPath, timepath, name);
							try {
								FileUtils.createFile(upath);
								FileUtils.saveInputStream2File(file, upath);
								String dpath = String.format("%s/%s/%s", dayPath, timepath, name);
								//  文件名及地址
								fileState.put("name",name);
								fileState.put("download",dpath);
							} catch (Exception e) {
								throw  new ServiceException("上传失败！");
							}
							fileStates.add(fileState);
						}

					}
				} catch (Exception e) {
					throw  new ServiceException("上传失败！");
				}
			}
		}
		return fileStates;
	}




    /**
     * 删除 某个项目下的所有文件
	 * @param file
	 */

	public static void delectFile(File file ,  String zipName ){
		String[] childFilePaths = file.list();
		for(String childFilePath : childFilePaths){
			File childFile=new File(file.getAbsolutePath()+"\\"+childFilePath);
			if(!childFile.getName().equals(".git") && !childFile.getName().equals(zipName)){
				removeFile(childFile);
			}
		}
	}
	private static void removeFile(File file){
		if(file.isFile()){//表示该文件不是文件夹
			file.delete();
		}else{
			//首先得到当前的路径
			String[] childFilePaths = file.list();
			for(String childFilePath : childFilePaths){
				File childFile=new File(file.getAbsolutePath()+"\\"+childFilePath);
				removeFile(childFile);
			}
			file.delete();
		}
	}









}
