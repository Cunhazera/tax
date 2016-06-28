package com.tax.service;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tax.entity.Product;
import com.tax.entity.QSale;
import com.tax.entity.Sale;
import com.tax.entity.SaleProduct;
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
		Product product = manager.find(Product.class, 2L);
		SaleProduct saleProduct = new SaleProduct();
		saleProduct.setProduct(product);
		sale.setProducts(Arrays.asList(saleProduct));
		saleProduct.setSale(sale);
		manager.persist(sale);
	}

	public List<Sale> listAllSales() {
		return query.listAll(qSale);
	}

}
