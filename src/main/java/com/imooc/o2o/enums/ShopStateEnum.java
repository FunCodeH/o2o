package com.imooc.o2o.enums;

/**
 * 店铺状态枚举
 * @author Administrator
 *
 */
public enum ShopStateEnum {
	
	CHECK(0, "审核中"), 
	OFFLINE(-1, "非法商铺"), 
	SUCCESS(1, "操作成功"), 
	PASS(2, "通过认证"), 
	INNER_ERROR(-1001, "内部系统错误"),
	NULL_SHOPID(-1002, "ShopId为空"),
	NULL_SHOP(-1003, "Shop信息为空");
	
	private int state;
	private String stateInfo;
	
	//构造器设置为私有
	private ShopStateEnum(int state, String stateInfo){
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 根据传入得state返回相应得enum值
	 * @param state
	 * @return
	 */
	public static ShopStateEnum stateOf(int state){
		for(ShopStateEnum stateEnum: values()){
			if(stateEnum.getState() == state){
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
}
