package com.imooc.o2o.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	
	//获取根路径:D:/java/project/imooc/o2o/target/classes/
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random random = new Random();
	
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	/**
	 * 将CommonsMultipartFile 转化成 file
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
		File newFile = new File(cFile.getOriginalFilename());
		
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	
	/**
	 * 生成缩略图，CommonsMultipartFile是spring文件上传得方法
	 * @param thumbnail
	 * @param targeAddr
	 * @return
	 */
	public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targeAddr){
		//客户上传得图片名称有时候很奇怪，这里统一生成，不使用原来得名称
		String realFileName = getRandomFileName();
		//获取扩展名
		String extension = getFileExtension(fileName);
		//生成文件路径
		makeDirPath(targeAddr); 
		//生成文件地址
		String relativeAddr = targeAddr + realFileName + extension;
		logger.debug("current relativeAddr is:" + relativeAddr);
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnailInputStream).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/watermark.jpg")) , 0.25f)
				.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;
	}
	
	/**
	 * 创建目标路径所涉及到得目录，即/home/work/xiangze/xxx.jpg, 那么home work xiangze
	 * 这三个文件夹都得自动创建
	 * @param targeAddr
	 */
	private static void makeDirPath(String targeAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targeAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}	
	}

	/**
	 * 获取输入文件流得扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名，当前年月日小时分钟秒数 + 五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		//获取随机5位数,10000-99999
		int randomNum = random.nextInt(8999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + randomNum;
	}

	public static void main(String[] args) throws IOException{
		String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println(basePath);
		//压缩图片并打水印
		Thumbnails.of(new File("D:/java/project/imooc/images/1.jpg")).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/watermark.jpg")) , 0.5f)
			.outputQuality(0.8f).toFile("D:/java/project/imooc/images/2.jpg");
	}

}
