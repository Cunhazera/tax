package com.tax.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tax.entity.Impost;
import com.tax.entity.Product;
import com.tax.entity.QProduct;
import com.tax.operation.QueryObject;
import com.tax.operation.ValidateObject;

@Stateless
public class ProductService {

	@Inject
	private QueryObject<Product> query;

	@Inject
	private ValidateObject validationClass;

	@PersistenceContext
	private EntityManager manager;

	private QProduct qProduct = QProduct.product;

	public Product insertProduct(Product product) throws Exception {
		product.setImpost(manager.find(Impost.class, product.getImpost().getId()));
		validationClass.validateObject(product);
		return query.save(product);
	}

	public List<Product> listAll() {
		return query.listAll(qProduct);
	}

	public Product findById(Long id) {
		return query.findById(qProduct, qProduct.id.eq(id));
	}

	public Product edit(Product product) throws Exception {
		product.setImpost(manager.find(Impost.class, product.getImpost().getId()));
		validationClass.validateObject(product);
		return query.editEntity(product);
	}

	public void delete(Long id) {
		query.delete(qProduct, qProduct.id.eq(id));
	}

}
