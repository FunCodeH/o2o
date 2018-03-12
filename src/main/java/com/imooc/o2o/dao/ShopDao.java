package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	
	/**
	 * 分页查询商铺信息
	 * @param shopCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);
	
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	
	/**
	 * 通过shopid 查询店铺信息
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	
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
