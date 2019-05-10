package org.mjjaenl.webapp.service.serviceimpl;

import java.io.Serializable;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.mjjaenl.webapp.service.GenericService;
import org.springframework.data.repository.CrudRepository;

public abstract class GenericServiceImpl<E extends Object, DAO extends CrudRepository<E, ? extends Serializable>> implements GenericService<E, DAO> {
	protected DAO repository;
	
	public GenericServiceImpl(DAO repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional(value=TxType.REQUIRED)
	public Iterable<E> findAll() {
		return repository.findAll();
	}
	
	public E save(E entity) {
		return repository.save(entity);
	}

	public DAO getRepository() {
		return repository;
	}

	public void setRepository(DAO repository) {
		this.repository = repository;
	}
}
