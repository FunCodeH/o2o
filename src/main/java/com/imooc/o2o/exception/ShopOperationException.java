package com.imooc.o2o.exception;

public class ShopOperationException extends RuntimeException {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3882011364906825791L;

	public ShopOperationException(String msg){
		super(msg);
	}
}
