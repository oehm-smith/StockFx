package com.tintuna.stockfx.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.application.TabStandardNames;
import com.tintuna.stockfx.controller.PrefsController;
import com.tintuna.stockfx.exception.StockFxPersistenceException;
import com.tintuna.stockfx.util.TabManagerParameters;

public class Crud {
	private static final Logger log = LoggerFactory.getLogger(Crud.class);
	private boolean databaseReady = false;

	// TODO - Get Injection working
	// @Inject EntityManager em;
	private static final String urlProperty = "javax.persistence.jdbc.url";
	private static final String dbLocationPrefix = "jdbc:derby:";// then "/Users/bsmith/tmp/stockfxDb"
	private static final String dbLocationSuffix = ";create=true";
	private static final String dbName = "stockFxDb";
	private static final String PERSISTENCE_UNIT_NAME = "stockFxPU";
	private EntityManagerFactory factory;
	private EntityManager em;

	public Crud() throws FileNotFoundException {
		Map<String, String> properties = new HashMap<>();
		// database dir needs to be set in preferences
		properties.put(urlProperty, getDbLocation());
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
	}

	private void beginTransacgtion() throws StockFxPersistenceException {
		// log.debug("Factory open:" + factory.isOpen());
		// if (Persistence.getPersistenceUtil().isLoaded(factory)) {
		// log.debug(PERSISTENCE_UNIT_NAME + " is loaded");
		// } else {
		// log.debug(PERSISTENCE_UNIT_NAME + " is NOT loaded");
		// }
		try {
			em = factory.createEntityManager();
			// log.debug("em:" + em.isOpen());
			em.getTransaction().begin();
		} catch (PersistenceException e) {
			throw new StockFxPersistenceException("Is the application / database already open?  "+ e.getMessage());
		}
	}

	private String getDbLocation() throws FileNotFoundException {
		String dbPath = null;
		dbPath = MainApplication.getModelFactory().getStockFxPreferences().getDatabaseURL();
		log.debug("dbpath:" + dbPath);
		File dbl = new File(dbPath + "/");
		if (!dbl.exists()) {
			String msg = "DB Path doesn't exist - please set in the Preferences.  Then restart this application (yes there is a ticket to improve this!)";
			MainApplication.setMessage(msg);
			log.debug(msg);
			setDatabaseReady(false);
			MainApplication.getAppFactory().getTabManager().addTabWithNode(TabStandardNames.Prefs.name(), MainApplication.getAppFactory().getPrefsController(),
					TabManagerParameters.startParams().insertAt(0).openNotAdd(true));
			PrefsController prefsController = MainApplication.getAppFactory().getPrefsController();
			prefsController.addDBPathLostFocusListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					if (arg1 == true && arg2 == false) {
						log.debug("DB Path changed (lost focus)");
						// TODO Issue 1 (https://github.com/oehm-smith/StockFx/issues/1) - fix here to re-init DB
					}
				}
			});
			throw new FileNotFoundException(msg);
		} else {
			setDatabaseReady(true);
			String location = dbLocationPrefix + dbPath + "/" + dbName + dbLocationSuffix;
			log.info("DB Location:" + location);
			return location;
		}
	}

	public boolean isDatabaseReady() {
		return databaseReady;
	}

	public void setDatabaseReady(boolean databaseReady) {
		this.databaseReady = databaseReady;
	}

	private void endTransaction() throws StockFxPersistenceException {
		try {
			em.getTransaction().commit();
			em.close();
		} catch (PersistenceException e) {
			throw new StockFxPersistenceException(e.getMessage());
		}
	}

	public <T> T create(T t) throws StockFxPersistenceException {
		beginTransacgtion();
		this.em.persist(t);
		// this.em.flush();
		// this.em.refresh(t);
		endTransaction();
		return t;
	}

	public <T> void delete(T t) throws StockFxPersistenceException {
		beginTransacgtion();
		this.em.remove(t);
		endTransaction();
	}

	public <T> void delete(Class<T> type, Object id) throws StockFxPersistenceException {
		beginTransacgtion();
		Object ref = this.em.getReference(type, id);
		this.em.remove(ref);
		endTransaction();
	}

	public <T> T update(T t) throws StockFxPersistenceException {
		beginTransacgtion();
		T t2 = (T) this.em.merge(t);
		endTransaction();
		return t2;
	}

	public <T> T find(Class<T> type, Integer id) throws StockFxPersistenceException {
		beginTransacgtion();
		T t2 = (T) this.em.find(type, id);
		endTransaction();
		return t2;
	}

	public List<?> findWithNamedQuery(String namedQueryName) throws StockFxPersistenceException {
		beginTransacgtion();
		List<?> t2 = this.em.createNamedQuery(namedQueryName).getResultList();
		endTransaction();
		return t2;
	}

	public List<?> findWithNamedQuery(String namedQueryName, QueryParameter parameters) throws StockFxPersistenceException {
		return findWithNamedQuery(namedQueryName, parameters, 0);
	}

	public List<?> findWithNamedQuery(String queryName, int resultLimit) throws StockFxPersistenceException {
		beginTransacgtion();
		List<?> t2 = this.em.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
		endTransaction();
		return t2;
	}

	public <T> List<T> findByNativeQuery(String sql, Class<T> type) throws StockFxPersistenceException {
		beginTransacgtion();
		@SuppressWarnings("unchecked")
		List<T> t2 = this.em.createNativeQuery(sql, type).getResultList();
		endTransaction();
		return t2;
	}

	public List<?> findWithNamedQuery(String namedQueryName, QueryParameter parameters, int resultLimit) throws StockFxPersistenceException {
		beginTransacgtion();
		Set<Entry<String, Object>> rawParameters = parameters.parameters().entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		List<?> t2 = query.getResultList();
		endTransaction();
		return t2;
	}
}
