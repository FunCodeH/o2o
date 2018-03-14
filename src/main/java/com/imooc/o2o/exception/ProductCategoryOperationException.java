package com.imooc.o2o.exception;

public class ProductCategoryOperationException extends RuntimeException{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3771849491646116037L;

	public ProductCategoryOperationException(String msg){
		super(msg);
	}
}
