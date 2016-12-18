package io.downgoon.jresty.data.cache.support.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.downgoon.jresty.data.cache.support.ICache;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class XmemcachedCacheImpl<K extends Serializable, V extends Serializable> implements ICache<K, V> {
	private static final Logger LOG = LoggerFactory.getLogger(XmemcachedCacheImpl.class);

	private MemcachedClient memcachedClient;
	private static final int DEFAULT_CONNECTION_POOL_SIZE = 1;
	private static final int DEFAULT_EXPIRATION = 0; // 缓存默认过期时间, 不过期

	public XmemcachedCacheImpl(String servers) throws Exception {
		this(servers, DEFAULT_CONNECTION_POOL_SIZE);
	}

	public XmemcachedCacheImpl(String servers, int connectionPoolSize) throws Exception {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));
		builder.setTranscoder(new ClassloaderSerializingTranscoder());
		builder.setConnectionPoolSize(connectionPoolSize);
		memcachedClient = builder.build();
	}

	@Override
	public V get(K key) {
		try {
			String cacheKey = getCacheKey(key);
			V v = (V) memcachedClient.get(cacheKey);
			if (LOG.isDebugEnabled()) {
				final Object[] logValue = new Object[] { key, v == null ? "null" : v };
				LOG.debug("get K[{}] V[{}]", logValue);
			}
			return v;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<V> getMulti(K[] keys) {
		try {
			List<String> cacheKeys = getCacheKey(keys);
			Map<String, V> valueMap = memcachedClient.get(cacheKeys);
			List<V> list = new ArrayList<V>();
			for (K k : keys) {
				String cacheKey = getCacheKey(k);
				list.add(valueMap.get(cacheKey));
			}
			if (LOG.isDebugEnabled()) {
				final Object[] logValue = new Object[] { keys.length, list.size() };
				LOG.debug("getMulti K.len[{}] List.len[{}]", logValue);
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void put(K key, V value) {
		this.put(key, value, DEFAULT_EXPIRATION);
	}

	@Override
	public void put(K key, V value, int expireTime) {
		try {
			String cacheKey = getCacheKey(key);
			memcachedClient.set(cacheKey, expireTime, value);
			if (LOG.isDebugEnabled()) {
				final Object[] logValue = new Object[] { key, value, expireTime };
				LOG.debug("put K[{}] V[{}] ET[{}]", logValue);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove(K key) {
		try {
			String cacheKey = getCacheKey(key);
			memcachedClient.delete(cacheKey);
			if (LOG.isDebugEnabled()) {
				LOG.debug("remove K[{}]", key);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private final String getCacheKey(K key) throws Exception {
		return URLEncoder.encode(key.toString(), "UTF-8");
	}

	private final List<String> getCacheKey(K[] keys) throws Exception {
		List<String> cacheKeys = new ArrayList<String>();
		for (int i = 0; keys != null && i < keys.length; i++) {
			String encodeK = URLEncoder.encode(keys[i].toString(), "UTF-8");
			cacheKeys.add(encodeK);
		}
		return cacheKeys;
	}

	static class ClassloaderSerializingTranscoder extends SerializingTranscoder {

		public ClassloaderSerializingTranscoder() {

		}

		@Override
		protected Object deserialize(byte[] in) {
			Object rv = null;
			ByteArrayInputStream bis = null;
			ObjectInputStream is = null;
			try {
				if (in != null) {
					bis = new ByteArrayInputStream(in);
					is = new ObjectInputStream(bis) {
						@Override
						protected Class<?> resolveClass(ObjectStreamClass desc)
								throws IOException, ClassNotFoundException {
							try {
								return Thread.currentThread().getContextClassLoader().loadClass(desc.getName());
							} catch (ClassNotFoundException e) {
								return super.resolveClass(desc);
							}
						}
					};
					rv = is.readObject();
				}
			} catch (IOException e) {
				LOG.error("Caught IOException decoding " + in.length + " bytes of data", e);
			} catch (ClassNotFoundException e) {
				LOG.error("Caught XMemcached decoding " + in.length + " bytes of data", e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
					}
				}
			}
			return rv;
		}
	}

}
