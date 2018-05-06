package com.zhide.daily.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
	
	/*
	 * 公共上传方法
	 * 上传文件目录
	 */
	@SuppressWarnings("unused")
	public static String excuteUpload(String uploadDri,MultipartFile file)throws Exception{
		
		List<String> List = new ArrayList<String>();
		
		String suffix = "";
		//文件后缀名
		if(file.getOriginalFilename().length()>0){
			suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		}
		//上传文件名
		String filename = UUID.randomUUID()+suffix;
		//服务器端保存的文件对象
		File serverFile = new File(uploadDri+filename);
		
		//将上传的文件写入到服务器文件内
		file.transferTo(serverFile);
		
		return filename;
		
	}

}
