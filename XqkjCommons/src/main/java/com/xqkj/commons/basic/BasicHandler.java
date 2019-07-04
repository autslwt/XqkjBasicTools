package com.xqkj.commons.basic;

public interface BasicHandler<R,T> {

	R doHandler(T param);
	
	String handlerKey();

}
