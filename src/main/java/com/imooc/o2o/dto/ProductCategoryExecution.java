package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.enums.ShopStateEnum;

public class ProductCategoryExecution {
	// 结果状态
		private int state;

		// 状态标识
		private String stateInfo;

		// ProductCategory列表 查询时使用
		private List<ProductCategory> productCategoryList;
		
		public ProductCategoryExecution() {

		}

		/**
		 * 店铺操作失败得时候使用得构造器
		 * 
		 * @param stateEnum
		 */
		public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

		/**
		 * 店铺操作成功得时候使用得构造器
		 * 
		 * @param stateEnum
		 */
		public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.productCategoryList = productCategoryList;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public String getStateInfo() {
			return stateInfo;
		}

		public void setStateInfo(String stateInfo) {
			this.stateInfo = stateInfo;
		}

		public List<ProductCategory> getProductCategoryList() {
			return productCategoryList;
		}

		public void setProductCategoryList(List<ProductCategory> productCategoryList) {
			this.productCategoryList = productCategoryList;
		}	
}
