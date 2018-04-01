package com.imooc.o2o.util;

public class PathUtil {
	private static String separator = System.getProperty("file.separator");
	
	/**
	 * 获取当前系统得图片basePath路径
	 * @return
	 */
	public static String getImgBasePath(){
		//获取当前使用得操作系统
		String os = System.getProperty("os.name");
		String basePath = "";
		
		if(os.toLowerCase().startsWith("win")){
			basePath = "D:/java/project/imooc/image";
		}else{
			basePath = "/home/xiangze/image";
		}
		
		basePath = basePath.replace("/", separator);
		return basePath;
	}
	
	/**
	 * 商铺图片放在各个商铺下面，以shopId区分,是相对路径
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId){
		String imagePath = "/upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", separator);
	}
}
