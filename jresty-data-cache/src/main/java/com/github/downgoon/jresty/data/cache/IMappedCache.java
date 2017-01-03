package com.github.downgoon.jresty.data.cache;

import java.io.Serializable;

public interface IMappedCache<PK extends Serializable> {

	PK get(String mappedName, Object[] conditions);

	void put(String mappedName, Object[] conditions, PK value);

	void remove(String mappedName, Object[] conditions);
}
