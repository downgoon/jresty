package io.downgoon.jresty.data.cache.support.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.downgoon.jresty.data.cache.support.ICache;

/**
 * Only For JUnit Testing
 * */
public class HashMapCache<K extends Serializable, V extends Serializable> implements ICache<K, V> {

	protected static final Log log = LogFactory.getLog(HashMapCache.class);
	
	protected Map<K,V> mapStore = new HashMap<K,V>();
	protected Map<K,Long> mapExpire = new HashMap<K,Long>();
	
	@Override
	public V get(K key) {
		V v = mapStore.get(key);
		if(v==null) {
			return null;
		}
		Long expireTime = mapExpire.get(key);
		if(expireTime==null || System.currentTimeMillis() < expireTime) {
			return v;  // 表示不过期 或 还没到过期时间
		}
		log.warn("MapCache的键过期告警：k="+key+",v="+v);
		return null; //过期了
	}

	@Override
	public List<V> getMulti(K[] keys) {
		if(keys==null) {
			return null;
		}
		List<V> list = new ArrayList<V>();
		for (K k : keys) {
			list.add(get(k));
		}
		return list;
	}

	@Override
	public void put(K key, V value) {
		mapStore.put(key, value);
	}

	@Override
	public void put(K key, V value, int expireTime) {
		mapStore.put(key, value);
		mapExpire.put(key, System.currentTimeMillis()+(expireTime*1000L));
	}

	@Override
	public void remove(K key) {
		mapStore.remove(key);
		mapExpire.remove(key);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("mapStore: ").append(mapStore).append("; ");
		sb.append("mapExpire: ").append(mapExpire).append("; ");
		return sb.toString();
	}
	
}

