package org.mjjaenl.webapp.service;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

public interface GenericService <E extends Object, DAO extends CrudRepository<E, ? extends Serializable>> {
	public Iterable<E> findAll();
	public E save(E entity);
}
