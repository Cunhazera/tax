package com.tax.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tax.entity.Impost;
import com.tax.entity.QImpost;
import com.tax.operation.QueryObject;
import com.tax.operation.ValidateObject;

@Stateless
public class ImpostService {

	@PersistenceContext
	private EntityManager manager;

	@Inject
	private ValidateObject validator;

	@Inject
	private QueryObject<Impost> query;

	private QImpost qImpost = QImpost.impost;

	public Impost insertImpost(Impost object) throws Exception {
		validator.validateObject(object);
		return query.save(object);
	}

	public Impost findById(Long id) {
		return query.findById(qImpost, qImpost.id.eq(id));
	}

	public List<Impost> listAll() {
		return query.listAll(qImpost);
	}

	public Impost edit(Impost impost) throws Exception {
		validator.validateObject(impost);
		return query.editEntity(impost);
	}

	public void delete(Long id) {
		query.delete(qImpost, qImpost.id.eq(id));
	}

}
