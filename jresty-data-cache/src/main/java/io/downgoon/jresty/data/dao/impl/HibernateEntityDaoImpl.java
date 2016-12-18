/**
 * 
 */
package io.downgoon.jresty.data.dao.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.downgoon.jresty.data.dao.IEntity;
import io.downgoon.jresty.data.dao.IEntityDao;

/**
 * 
 * @date 2009-7-13
 * 
 * @param <E>
 * @param <PK>
 */
public class HibernateEntityDaoImpl<E extends IEntity<PK>, PK extends Serializable> extends HibernateDaoSupport implements IEntityDao<E, PK> {
	private static final Logger log = LoggerFactory.getLogger(HibernateEntityDaoImpl.class);
	private static final long TIMEOUT = 50; // 超时阈值

	@Override
	public E get(final Class<? extends E> clazz, final PK id) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				Object entity = session.get(clazz, id);
				return entity;
			}
		};
		Object obj = getHibernateTemplate().execute(callback);
		if (log.isDebugEnabled()) {
			Object[] logValue = new Object[] { clazz, id, obj == null ? null : obj.getClass() };
			log.debug("get - class:{}, id:{}, ret:{}", logValue);
		}
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, clazz, id };
			log.warn("get - Time:{} clazz:{} id:{}", logValue);
		}
		return (E) obj;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = java.lang.Exception.class)
	public PK save(final E e) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				Serializable id = session.save(e);
				e.setId((PK) id);
				return e.getId();
			}
		};
		PK id = (PK) getHibernateTemplate().execute(callback);
		log.debug("save - class:{}, id:{}", e.getClass(), id == null ? null : id);
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, e.getClass(), id };
			log.warn("save - Time:{} clazz:{} id:{}", logValue);
		}
		return id;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = java.lang.Exception.class)
	public void update(final E e) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				session.update(e);
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
		log.debug("update - class:{}, id:{}", e.getClass(), e.getId());
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, e.getClass(), e.getId() };
			log.warn("update - Time:{} clazz:{} id:{}", logValue);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = java.lang.Exception.class)
	public void delete(final E e) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				session.delete(e);
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
		log.debug("delete - class:{}, id:{}", e.getClass(), e.getId());
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, e.getClass(), e.getId() };
			log.warn("update - Time:{} clazz:{} id:{}", logValue);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = java.lang.Exception.class)
	public void delete(final Class<? extends E> clazz, final PK id) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				E e = get(clazz, id);
				if (e != null) {
					session.delete(e);
				}
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
		log.debug("delete - class:{}, id:{}", clazz, id);
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, clazz, id };
			log.warn("update - Time:{} clazz:{} id:{}", logValue);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = java.lang.Exception.class)
	public void saveOrUpdate(final E e) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				session.saveOrUpdate(e);
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
		log.debug("saveOrUpdate - class:{}, id:{}", e.getClass(), e.getId());
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, e.getClass(), e.getId() };
			log.warn("saveOrUpdate - Time:{} clazz:{} id:{}", logValue);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = java.lang.Exception.class)
	public int batchUpdate(final String sql, final List<Object> params) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) {
				Query query = session.createSQLQuery(sql);
				int k = 0;
				for (int i = 0; params != null && i < params.size(); i++) {
					query.setParameter(k++, params.get(i));
				}
				return query.executeUpdate();
			}
		};
		Object obj = getHibernateTemplate().execute(callback);
		log.debug("batchUpdate - sql:{}, updated:{}", sql, obj);
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, sql, params };
			log.warn("batchUpdate - Time:{} sql:{} params:{}", logValue);
		}
		return (Integer) obj;
	}

	@Override
	public List<PK> getHQLList(final String hql, final Object[] params, final int maxResults) {
		final long tb = System.currentTimeMillis();
		HibernateCallback<List<PK>> callback = new HibernateCallback<List<PK>>() {
			@Override
			public List<PK> doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				int k = 0;
				for (int i = 0; params != null && i < params.length; i++) {
					query.setParameter(k++, params[i]);
				}
				if (maxResults > -1) {
					query.setMaxResults(maxResults);
				}
				return query.list();
			}
		};
		List<PK> list = getHibernateTemplate().execute(callback);
		if (log.isDebugEnabled()) {
			Object[] logValue = new Object[] { hql, params, maxResults, list.size() };
			log.debug("getHQLList - hql:{}, params:{}, maxResults:{}, list.len:{}", logValue);
		}
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, hql, params, maxResults };
			log.warn("batchUpdate - Time:{} sql:{} params:{} maxResults:{}", logValue);
		}
		return new LinkedList<PK>(list);
	}

	@Override
	public List<PK> getHQLList(final String hql, final Object[] params, final int start, final int size) {
		final long tb = System.currentTimeMillis();
		HibernateCallback<List<PK>> callback = new HibernateCallback<List<PK>>() {
			@Override
			public List<PK> doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				int k = 0;
				for (int i = 0; params != null && i < params.length; i++) {
					query.setParameter(k++, params[i]);
				}
				query.setFirstResult(start);
				query.setMaxResults(size);
				return query.list();
			}
		};
		List<PK> list = getHibernateTemplate().execute(callback);
		if (log.isDebugEnabled()) {
			Object[] logValue = new Object[] { hql, params, start, size, list.size() };
			log.debug("getHQLList - hql:{}, params:{}, start:{}, size:{}, list.len:{}", logValue);
		}
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, hql, params, start, size };
			log.warn("batchUpdate - Time:{} sql:{} params:{} start:{} size:{}", logValue);
		}
		return new LinkedList<PK>(list);
	}
}
