package com.imooc.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException{
		//空值判断
		if(shop == null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		
		try {
			//店铺信息初始值
			shop.setEnableStatus(0); //审核中
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加
			int effectedNum = shopDao.insertShop(shop);
			if(effectedNum <= 0){
				//使用RuntimeException才能在操作失败时进行事务回滚
				throw new ShopOperationException("店铺创建失败");
			}else{
				if(shopImgInputStream != null){
					//存储图片
					try {
						addShopImg(shop, shopImgInputStream, fileName);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					//跟新店铺图片地址
					effectedNum = shopDao.updateShop(shop);
					if(effectedNum <= 0){
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
		// 获取shop图片目录得相对路径,以店铺ID作为区分文件目录
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);	
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(Long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName)
			throws ShopOperationException {
		
		if(shop == null || shop.getShopId() == null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		//1.判断是否需要处理图片
		try {
			if(shopImgInputStream != null && fileName != null && !"".equals(fileName)){
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg()!=null){
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop, shopImgInputStream, fileName);
			}
			//2.跟新店铺信息
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.updateShop(shop);
			if(effectedNum <= 0){
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			}else{
				shop = shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS, shop);
			}
		} catch (Exception e) {
			throw new ShopOperationException("modifyShop error:" + e.getMessage());
		}
	}
}
