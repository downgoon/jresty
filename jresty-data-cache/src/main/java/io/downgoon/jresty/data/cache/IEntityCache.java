package io.downgoon.jresty.data.cache;

import java.util.List;

import io.downgoon.jresty.data.dao.IEntity;

public interface IEntityCache<E, PK> {

	E get(Class<? extends IEntity<PK>> clazz, PK id);

	List<E> getMulti(Class<? extends IEntity<PK>> clazz, PK[] ids);

	void put(E e);

	void remove(Class<? extends IEntity<PK>> clazz, PK id);
}
