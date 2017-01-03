package com.github.downgoon.jresty.data.cache.support;

import java.util.List;

public interface ICache<K, V> {

	V get(K key);

	List<V> getMulti(K[] keys);

	void put(K key, V value);

	void put(K key, V value, int expireTime);

	void remove(K key);

}
