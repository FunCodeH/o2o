package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	
	/**
	 * 插入店铺信息，插入成功返回1，表示插入一条记录，插入失败返回-1，由mybatis返回
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 跟新店铺信息
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
