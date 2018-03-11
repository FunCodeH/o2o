package com.imooc.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * 获取店铺信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getshopbyid", method={RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getshopById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId > -1){
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
	/**
	 * 修改商铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modifyshop", method={RequestMethod.POST})
	@ResponseBody   //转化成json格式
	private Map<String, Object> modifyShop(HttpServletRequest request){
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		//验证码判断
		if(!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入验证码错误");
			return modelMap;
		}
		
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
		}
		//2、修改商铺信息
		if(shop != null && shop.getShopId() != null){
			ShopExecution se;
			try {
				if(shopImg == null){
					se = shopService.modifyShop(shop, null, null);
				}else{
					se = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				}
				if(se.getState() == ShopStateEnum.SUCCESS.getState()){
					modelMap.put("success", true);
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺id");
			return modelMap;
		}
	}
	
	/**
	 * 获取店铺初始化信息，区域，店铺类别等
	 */
	@RequestMapping(value="/getshopinitinfo", method={RequestMethod.GET})
	@ResponseBody
	private Map<String, Object> getshopInitInfo(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
		List<Area> areaList = areaService.getAreaList();
		modelMap.put("shopCategoryList", shopCategoryList);
		modelMap.put("areaList", areaList);
		modelMap.put("success", true);
		return modelMap;
	}
	/**
	 * 注册商铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/registershop", method={RequestMethod.POST})
	@ResponseBody   //转化成json格式
	private Map<String, Object> registerShop(HttpServletRequest request){
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		//验证码判断
		if(!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入验证码错误");
			return modelMap;
		}
		
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
			PersonInfo oInfo = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(oInfo);
			ShopExecution se;
			try {
				se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if(se.getState() == ShopStateEnum.CHECK.getState()){
					modelMap.put("success", true);
					//该用户可以操作得店铺列表放入session中
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if(shopList == null || shopList.size() == 0){
						shopList = new ArrayList<Shop>(); 
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
	}
	
	//通过输入流转化CommonsMultipartFile 到 file
	@Deprecated
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
