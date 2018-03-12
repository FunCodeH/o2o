package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	
	/**
	 * 查询商铺下得商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(Long shopId);
}
