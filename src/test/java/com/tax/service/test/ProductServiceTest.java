package com.tax.service.test;

import static com.tax.entity.Product.builder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Validator;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.querydsl.jpa.impl.JPAQuery;
import com.tax.entity.Impost;
import com.tax.entity.Product;
import com.tax.entity.QProduct;
import com.tax.operation.QueryObject;
import com.tax.operation.ValidateObject;
import com.tax.service.ProductService;

public class ProductServiceTest {

	@InjectMocks
	private ProductService service;
	@Mock
	private EntityManager entityManager;
	@Mock
	private QueryObject<Product> query;
	@Mock
	private ValidateObject validator;
	@Mock
	private Validator valid;
	@Mock
	private JPAQuery<Product> jpa;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void validateDetails() {
		Product product = builder().id(1L).price(10D).name("Product")
				.impost(buildImpost("TnksDilma", new BigDecimal(2))).build();

		assertThat(product.getId(), equalTo(1L));
		assertThat(product.getName(), equalTo("Product"));
		assertThat(product.getPrice(), equalTo(10D));
		assertThat(product.getImpost(), notNullValue());
		assertThat(product.getImpost().getName(), equalTo("TnksDilma"));
		assertThat(product.getImpost().getPercent(), equaasdsdadadsalTo(new BigDecimal(2)));
	}

	@Test
	public void testNoArgsConstructor() {
		Product prod = new Product();
		Impost impost = new Impost();
		impost.setId(1L);
		impost.setName("imp");
		impost.setPercent(new BigDecimal(10));
		prod.setId(1L);
		prod.setImpost(impost);
		prod.setName("name");
		prod.setPrice(10D);
		
		assertThat(impost.getId(), notNullValue());
		assertThat(impost.getPercent(), notNullValue());
		assertThat(impost.getName(), notNullValue());
		assertThat(impost.hashCode(), notNullValue());
		assertThat(impost.equals(impost), Matchers.is(true));
		assertThat(impost.toString(), notNullValue());
		assertThat(impost, notNullValue());
		
		assertThat(prod.hashCode(), notNullValue());
		assertThat(prod.equals(prod), Matchers.is(true));
		assertThat(prod.toString(), notNullValue());
		assertThat(prod, notNullValue());
	}

	@Test
	public void insertProduct() throws Exception {
		Impost impost = new Impost(1L, "Impost", new BigDecimal(10));
		Product entity = createProduct(1L, null, 2D, impost);
		when(entityManager.find(Impost.class, entity.getImpost().getId())).thenReturn(impost);
		when(service.insertProduct(entity)).thenReturn(entity);
		Product newProduct = service.insertProduct(entity);
		verify(query, times(1)).save(entity);
		assertThat(newProduct.getId(), equalTo(1L));
	}

	@Test
	public void listAllTest() {
		List<Product> list = new ArrayList<>();
		list.add(createProduct(1L, "Product1", 20D, buildImpost("Impost1", new BigDecimal(50))));
		list.add(createProduct(2L, "Product2", 50D, buildImpost("Impost2", new BigDecimal(10))));
		when(service.listAll()).thenReturn(list);
		List<Product> result = service.listAll();
		assertThat(result.size(), equalTo(2));
	}

	@Test
	public void findById() {
		Product prod = createProduct(1L, "Prod", 100D, buildImpost("impost", new BigDecimal(10)));
		when(service.findById(1L)).thenReturn(prod);
		Product product = service.findById(1L);
		assertThat(product.getName(), equalTo("Prod"));
	}

	@Test
	public void delete() {
		QProduct qProduct = QProduct.product;
		Product prod = createProduct(1L, "Prod", 100D, buildImpost("impost", new BigDecimal(10)));
		service.delete(prod.getId());
		Mockito.verify(query).delete(qProduct, qProduct.id.eq(prod.getId()));
	}
	
	@Test
	public void edit() throws Exception {
		Impost impost = buildImpost("imp", new BigDecimal(10));
		Product product = createProduct(1L, "prod", 20D, impost);
		when(entityManager.find(Impost.class, product.getImpost().getId())).thenReturn(impost);
		when(query.editEntity(product)).thenReturn(createProduct(1L, "prod", 20D, impost));
		query.editEntity(product);
		Product newProd = service.edit(product);
		assertThat(newProd.getName(), equalTo("prod"));
	}

	public Product createProduct(long id, String name, Double price, Impost impost) {
		return Product.builder().id(id).name(name).price(price).impost(impost).build();
	}

	public Impost buildImpost(String name, BigDecimal percent) {
		return Impost.builder().name(name).percent(percent).build();
	}

}
