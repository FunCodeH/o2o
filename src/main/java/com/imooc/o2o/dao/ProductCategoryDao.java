package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	
	/**
	 * 查询商铺下得商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(Long shopId);
	
	/**
	 * 批量新增商品类别
	 * @param ProductCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> ProductCategoryList);
	
	/**
	 * 删除指定商品类别
	 * 
	 * @param productCategoryId
	 * @param shopId
	 * @return effectedNum
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);

}
