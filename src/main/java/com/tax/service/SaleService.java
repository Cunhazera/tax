package com.tax.service;

import static java.util.Arrays.asList;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tax.entity.Product;
import com.tax.entity.QSale;
import com.tax.entity.Sale;
import com.tax.entity.SaleProductEntity;
import com.tax.operation.QueryObject;
import com.tax.operation.ValidateObject;

@Stateless
public class SaleService {

	@PersistenceContext
	private EntityManager manager;

	@Inject
	private ValidateObject validator;

	@Inject
	private QueryObject<Sale> query;

	private QSale qSale = QSale.sale;

	public void insertSale(Sale sale) throws Exception {
		SaleProductEntity saleItem = new SaleProductEntity();
		saleItem.setProduct(manager.find(Product.class, sale.getProducts().get(0).getProduct().getId()));
		sale.setProducts(asList(saleItem));
		validator.validateObject(sale);
		query.save(sale);
	}

	public List<Sale> listAllSales() {
		return query.listAll(qSale);
	}

}
