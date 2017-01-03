package com.github.downgoon.jresty.data.cache.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.github.downgoon.jresty.data.cache.IMappedCache;
import com.github.downgoon.jresty.data.cache.support.ICache;
import com.github.downgoon.jresty.data.cache.util.CacheKeyUtil;

@Component
public class MappedCacheImpl<PK extends Serializable> implements IMappedCache<PK> {

	private ICache<String, PK> cache;

	@Override
	public PK get(String mappedName, Object[] conditions) {
		String cacheKey = CacheKeyUtil.getMappedCacheKey(mappedName, conditions);
		return cache.get(cacheKey);
	}

	@Override
	public void put(String mappedName, Object[] conditions, PK value) {
		String cacheKey = CacheKeyUtil.getMappedCacheKey(mappedName, conditions);
		cache.put(cacheKey, value);
	}

	@Override
	public void remove(String mappedName, Object[] conditions) {
		String cacheKey = CacheKeyUtil.getMappedCacheKey(mappedName, conditions);
		cache.remove(cacheKey);
	}

	public ICache<String, PK> getCache() {
		return cache;
	}

	public void setCache(ICache<String, PK> cache) {
		this.cache = cache;
	}
	

}
