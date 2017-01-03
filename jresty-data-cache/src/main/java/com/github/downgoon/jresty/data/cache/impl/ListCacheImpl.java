package com.github.downgoon.jresty.data.cache.impl;

import java.io.Serializable;
import java.util.List;

import com.github.downgoon.jresty.data.cache.IListCache;
import com.github.downgoon.jresty.data.cache.support.ICache;
import com.github.downgoon.jresty.data.cache.util.CacheKeyUtil;

public class ListCacheImpl<T extends Serializable> implements IListCache<T> {

	private ICache<String, List<T>> cache;

	@Override
	public List<T> get(String listCacheName, Object[] conditions) {
		String listCacheKey = CacheKeyUtil.getListCacheKey(listCacheName, conditions);
		List<T> list = cache.get(listCacheKey);
		return list;
	}

	@Override
	public void put(String listCacheName, Object[] conditions, List<T> value) {
		String listCacheKey = CacheKeyUtil.getListCacheKey(listCacheName, conditions);
		cache.put(listCacheKey, value);
	}

	@Override
	public void addElement(String listCacheName, Object[] conditions, T element, int maxCacheSize) {
		addElement(listCacheName, conditions, element, maxCacheSize, false);
	}

	@Override
	public void addElement(String listCacheName, Object[] conditions, T element, int maxCacheSize, boolean append) {
		String listCacheKey = CacheKeyUtil.getListCacheKey(listCacheName, conditions);
		List<T> list = cache.get(listCacheKey);
		if (list == null) {
			// not in listcache, do nothing
			return;
		}
		if (append) {
			list.add(element);
			if (list.size() > maxCacheSize) {
				list.remove(0);
			}
		} else {
			list.add(0, element);
			if (list.size() > maxCacheSize) {
				list.remove(list.size() - 1);
			}
		}
		cache.put(listCacheKey, list);
	}

	@Override
	public void removeElement(String listCacheName, Object[] conditions, T element) {
		String listCacheKey = CacheKeyUtil.getListCacheKey(listCacheName, conditions);
		List<T> list = cache.get(listCacheKey);
		list.remove(element);
		cache.put(listCacheKey, list);
	}

	@Override
	public void remove(String listCacheName, Object[] conditions) {
		String listCacheKey = CacheKeyUtil.getListCacheKey(listCacheName, conditions);
		cache.remove(listCacheKey);
	}

	public ICache<String, List<T>> getCache() {
		return cache;
	}

	public void setCache(ICache<String, List<T>> cache) {
		this.cache = cache;
	}

}
