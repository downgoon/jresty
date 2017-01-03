package com.github.downgoon.jresty.data.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author downgoon
 * 
 * @date 2010-11-4
 * 
 * @param <E>
 * @param <PK>
 */
public interface IEntityDao<E extends IEntity<PK>, PK extends Serializable> {

	E get(Class<? extends E> clazz, PK id);

	PK save(E e);

	void update(E e);

	int batchUpdate(String sql, List<Object> params);

	void saveOrUpdate(E e);

	void delete(Class<? extends E> clazz, PK id);

	void delete(E e);

	List<PK> getHQLList(String hql, Object[] params, int maxResults);

	List<PK> getHQLList(String hql, Object[] params, int start, int size);
	
	List<Object> execHQLList(String hql, Object[] params, int maxResults);

	List<Object> execHQLList(String hql, Object[] params, int start, int size);

}
