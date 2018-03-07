package com.imooc.o2o.entity;

import java.util.Date;

/**
 * 区域实体类
 * @author funcodeh
 *
 */
public class Area {

	//全部使用包装类，不使用基本数据类型，原因是保证不赋值的情况下为空，如果是基本数据类型，不初始化时默认值为0
	private Integer areaId;
	private String areaName;
	// 权重
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return lastEditTime;
	}
	public void setUpdateTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
}
