/**
 *
 */
package com.github.downgoon.jresty.data.cache.util;

import java.util.List;

/**
 * @date 2010-11-7
 * 
 */
public final class CacheKeyUtil {

	public static final String ENTITY_CACHE = "E";
	public static final String LIST_CACHE = "L";
	public static final String MAP_CACHE = "M";
	public static final String PAGE_CACHE = "P";

	public static final String getEntityCacheKey(Class<?> clazz, Object id) {
		return new StringBuilder(ENTITY_CACHE).append(":").append(clazz.getCanonicalName()).append("|").append(id).toString();
	}

	public static final String[] getEntityCacheKey(Class<?> clazz, Object[] ids) {
		String[] entityCacheKeys = new String[ids.length];
		for (int i = 0; ids != null && i < ids.length; i++) {
			entityCacheKeys[i] = new StringBuilder(ENTITY_CACHE).append(":").append(clazz.getCanonicalName()).append("|").append(ids[i]).toString();
		}
		return entityCacheKeys;
	}

	public static final String getListCacheKey(String listName, Object[] conditions) {
		StringBuilder sb = new StringBuilder();
		sb.append(LIST_CACHE);
		sb.append(":");
		sb.append(listName);
		for (int i = 0; conditions != null && i < conditions.length; i++) {
			sb.append("|").append(conditions[i]);
		}
		return sb.toString();
	}

	public static final String getPageCacheKey(String listName, List<Object> conditions) {
		StringBuilder sb = new StringBuilder();
		sb.append(PAGE_CACHE);
		sb.append(":");
		sb.append(listName);
		for (int i = 0; i < conditions.size(); i++) {
			sb.append("|").append(conditions.get(i));
		}
		return sb.toString();
	}

	public static final String getMappedCacheKey(String mappedName, Object[] conditions) {
		StringBuilder sb = new StringBuilder();
		sb.append(MAP_CACHE);
		sb.append(":");
		sb.append(mappedName);
		for (int i = 0; i < conditions.length; i++) {
			sb.append("|").append(conditions[i]);
		}
		return sb.toString();
	}

}
