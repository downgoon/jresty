package com.github.downgoon.jresty.data.cache;

import java.util.List;

public interface IListCache<T> {

	List<T> get(String listCacheName, Object[] conditions);

	void put(String listCacheName, Object[] conditions, List<T> value);

	void remove(String listCacheName, Object[] conditions);

	void addElement(String listCacheName, Object[] conditions, T element, int maxCacheSize);

	void addElement(String listCacheName, Object[] conditions, T element, int maxCacheSize, boolean append);

	void removeElement(String listCacheName, Object[] conditions, T element);

}
