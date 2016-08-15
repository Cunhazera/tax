package com.tax.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tax.entity.QSale;
import com.tax.entity.Sale;
import com.tax.operation.QueryObject;

@Stateless
public class SaleService {

	@PersistenceContext
	private EntityManager manager;

	@Inject
	private QueryObject<Sale> query;

	private QSale qSale = QSale.sale;

	public void insertSale(Sale sale) throws Exception {
		manager.persist(sale);
	}

	public List<Sale> listAllSales() {
		return query.listAll(qSale);
	}

}
