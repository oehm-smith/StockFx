package com.tintuna.stockfx.service;

import java.util.List;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.db.Crud;
import com.tintuna.stockfx.db.QueryParameter;
import com.tintuna.stockfx.exception.StockFxPersistenceException;
import com.tintuna.stockfx.exception.ValidationException;

public abstract class AbstractService<T> {

	// TODO - get Injection working
	// @Inject protected CrudService crud;
	Crud crud = MainApplication.getAppFactory().getCrudService();

	/**
	 * Pass-through to allow service users to call crud operations directly - such as all the find*() methods
	 * 
	 * @return
	 */
	private Crud getCrudService() {
		return crud;
	}

	public T create(final T item) throws StockFxPersistenceException {
		if (item == null) {
			throw new ValidationException("AbstractService<T> / create() - object is null.");
		}
		crud.create(item);

		return item;
	}

	public T update(final T item) throws StockFxPersistenceException {
		if (item == null) {
			throw new ValidationException("AbstractService<T> / update() - object is null.");
		}
		crud.update(item);

		return item;
	}

	public void delete(final T item) throws StockFxPersistenceException {
		if (item == null) {
			throw new ValidationException("AbstractService<T> / delete() - object is null.");
		}
		crud.delete(item);
	}

	/**
	 * The service classes that extend this should be named <entity>Service to enable this to work.
	 * 
	 * @return all of the entities (that extend this abstract class.
	 */
	public List<T> findAll() throws StockFxPersistenceException {
		// Expect all Entity classes to have namedQueries and in-particular "<entity class name>.FIND_ALL"
		// The service classes that extend this should be named <entity>Service
		String namedQueryString = this.getClass().getSimpleName().replace("Service", "") + ".findAll";
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) crud.findWithNamedQuery(namedQueryString);
		return list;
	}

	public T find(Class<T> type, String id) throws StockFxPersistenceException {
		String s = id.replaceAll("\"", "");
		Integer i = Integer.parseInt(s);
		return (T) getCrudService().find(type, i);
	}

	@SuppressWarnings("unchecked")
	public List<T> findWithNamedQuery(String namedQueryName, QueryParameter parameters) throws StockFxPersistenceException {
		return (List<T>) crud.findWithNamedQuery(namedQueryName, parameters);
	}
}
