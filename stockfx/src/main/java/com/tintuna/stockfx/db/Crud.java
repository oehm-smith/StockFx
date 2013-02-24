package com.tintuna.stockfx.db;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Crud {
	// TODO - Get Injection working
	// @Inject EntityManager em;
	private static final String PERSISTENCE_UNIT_NAME = "stockFxPU";
	private EntityManager em;

	private void beginTransacgtion() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = factory.createEntityManager();
		em.getTransaction().begin();
	}

	private void endTransaction() {
		em.getTransaction().commit();
		em.close();
	}

	public <T> T create(T t) {
		beginTransacgtion();
		this.em.persist(t);
		this.em.flush();
		this.em.refresh(t);
		endTransaction();
		return t;
	}

	public <T> void delete(T t) {
		beginTransacgtion();
		this.em.remove(t);
		endTransaction();
	}

	public <T> void delete(Class<T> type, Object id) {
		beginTransacgtion();
		Object ref = this.em.getReference(type, id);
		this.em.remove(ref);
		endTransaction();
	}

	public <T> T update(T t) {
		beginTransacgtion();
		T t2 = (T) this.em.merge(t);
		endTransaction();
		return t2;
	}

	public <T> T find(Class<T> type, Integer id) {
		beginTransacgtion();
		T t2 =  (T) this.em.find(type, id);
		endTransaction();
		return t2;
	}

	public List<?> findWithNamedQuery(String namedQueryName) {
		beginTransacgtion();
		List<?> t2 =  this.em.createNamedQuery(namedQueryName).getResultList();
		endTransaction();
		return t2;
	}

	public List<?> findWithNamedQuery(String namedQueryName, QueryParameter parameters) {
		return findWithNamedQuery(namedQueryName, parameters, 0);
	}

	public List<?> findWithNamedQuery(String queryName, int resultLimit) {
		beginTransacgtion();
		List<?> t2 =  this.em.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
		endTransaction();
		return t2;
	}

	public <T> List<T> findByNativeQuery(String sql, Class<T> type) {
		beginTransacgtion();
		@SuppressWarnings("unchecked")
		List<T> t2 =  this.em.createNativeQuery(sql, type).getResultList();
		endTransaction();
		return t2;
	}

	public List<?> findWithNamedQuery(String namedQueryName, QueryParameter parameters, int resultLimit) {
		beginTransacgtion();
		Set<Entry<String, Object>> rawParameters = parameters.parameters().entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		List<?> t2 =  query.getResultList();
		endTransaction();
		return t2;
	}
}
