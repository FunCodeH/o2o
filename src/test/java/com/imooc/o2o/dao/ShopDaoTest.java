package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testInsertShop(){
		Shop shop = new Shop();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		PersonInfo personInfo = new PersonInfo();

		shopCategory.setShopCategoryId(1L);
		personInfo.setUserId(1L);
		area.setAreaId(1);
		
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setOwner(personInfo);
		shop.setShopName("旗花面");
		
		shop.setShopName("测试的店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("审核中");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testUpdateShop(){
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopAddr("大寨路");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
}
