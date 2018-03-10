package com.imooc.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	
	@Autowired
	private ShopService shopService;
	
	/**
	 * 注册商铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/registershop", method=RequestMethod.POST)
	@ResponseBody   //转化成json格式
	private Map<String, Object> registerShop(HttpServletRequest request){
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//1、接受并转化相应得参数，包括商铺信息于图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try{
			shop = mapper.readValue(shopStr, Shop.class);
		}catch(Exception e){
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		
		//接受图片
		CommonsMultipartFile shopImg = null;
		//从本次会话得上下文获取相关的文件
		CommonsMultipartResolver cResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//判断request是否有上传得文件流
		if(cResolver.isMultipart(request)){
			//强制转化
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2、注册商铺
		if(shop != null && shopImg != null){
			PersonInfo oInfo = new PersonInfo();
			oInfo.setUserId(1L);
			shop.setOwner(oInfo);
			File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
			try {
				shopImgFile.createNewFile();
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			try {
				inputStreamToFile(shopImg.getInputStream(), shopImgFile);
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			ShopExecution se = shopService.addShop(shop, shopImgFile);
			if(se.getState() == ShopStateEnum.CHECK.getState()){
				modelMap.put("success", true);
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
	}
	
	//通过输入流转化CommonsMultipartFile 到 file
	private static void inputStreamToFile(InputStream ins, File file){
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while((bytesRead = ins.read(buffer)) != -1){
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException("调用inputStreamToFile产生异常：" + e.getMessage());
		}finally{
			try {
				if(os != null){
					os.close();
				}
				if(ins != null){
					ins.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("调用inputStreamToFile产生异常：" + e.getMessage());
			}
		}	
	}
}
