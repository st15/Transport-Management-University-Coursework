package com.trans.dao;

import java.util.List;

/**
 * @author Lili
 *
 */
public interface IDao<T> {

	public abstract List<T> findAll();

	public abstract void create(T item);

	public abstract void delete(Integer id);

	public abstract void update(T item);

	public abstract T find(Integer id);
}
