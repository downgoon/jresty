package com.github.downgoon.jresty.data.dao;

/**
 * @author downgoon
 * @date 2009-7-13
 * 
 */
public interface IEntity<PK> {

	PK getId();
	
	void setId(PK id);
}
