package io.downgoon.jresty.data.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.downgoon.jresty.data.cache.IEntityCache;
import io.downgoon.jresty.data.cache.support.ICache;
import io.downgoon.jresty.data.cache.util.CacheKeyUtil;
import io.downgoon.jresty.data.dao.IEntity;

public class EntityCacheImpl<E extends IEntity<PK>, PK extends Serializable> implements IEntityCache<E, PK> {

	private static final Logger LOG = LoggerFactory.getLogger(EntityCacheImpl.class);

	private ICache<String, E> cache;

	@Override
	public E get(Class<? extends IEntity<PK>> clazz, PK id) {
		String cacheKey = CacheKeyUtil.getEntityCacheKey(clazz, id);
		return cache.get(cacheKey);
	}

	@Override
	public List<E> getMulti(Class<? extends IEntity<PK>> clazz, PK[] ids) {
		final String[] cacheKeys = CacheKeyUtil.getEntityCacheKey(clazz, ids);
		List<E> list = cache.getMulti(cacheKeys);

		if (list != null) {
			List<PK> missIdList = new ArrayList<PK>();
			// 过滤空值元素
			for (int i = ids.length - 1; i >= 0; i--) {
				E e = list.get(i);
				if (e == null) {
					e = this.get(clazz, ids[i]);
					if (e == null) {

						list.remove(i);

						missIdList.add(ids[i]);
					} else {
						list.set(i, e);
					}
				}
			}

			if (list.size() != ids.length) {
				final Object[] logValue = new Object[] { Arrays.asList(cacheKeys), missIdList, cacheKeys.length, list.size() };
				LOG.warn("getMulti from cache, value contains null value. K[{}] Miss[{}] K.len[{}] List.len[{}]", logValue);
			}
			return list;
		}

		// 不使用cache, 从数据库取
		list = new ArrayList<E>();
		List<PK> missIdList = new ArrayList<PK>();
		for (int i = 0; i < ids.length; i++) {
			E e = get(clazz, ids[i]);
			if (e != null) {
				list.add(e);
			} else {
				list.add(e);
				missIdList.add(ids[i]);
			}
		}
		if (list.size() != ids.length) {
			final Object[] logValue = new Object[] { Arrays.asList(ids), missIdList, ids.length, list.size() };
			LOG.warn("getMulti from db, value contains null value. ids[{}] Miss[{}] ids.len[{}] List.len[{}]", logValue);
		}
		return list;
	}

	@Override
	public void put(E e) {
		String cacheKey = CacheKeyUtil.getEntityCacheKey(e.getClass(), e.getId());
		cache.put(cacheKey, e);
	}

	@Override
	public void remove(Class<? extends IEntity<PK>> clazz, PK id) {
		String cacheKey = CacheKeyUtil.getEntityCacheKey(clazz, id);
		cache.remove(cacheKey);
	}

	public ICache<String, E> getCache() {
		return cache;
	}

	public void setCache(ICache<String, E> cache) {
		this.cache = cache;
	}
	
}
