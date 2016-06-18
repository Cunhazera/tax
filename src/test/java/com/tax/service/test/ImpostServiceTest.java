package com.tax.service.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.querydsl.jpa.impl.JPAQuery;
import com.tax.entity.Impost;
import com.tax.entity.QImpost;
import com.tax.operation.QueryObject;
import com.tax.operation.ValidateObject;
import com.tax.service.ImpostService;

public class ImpostServiceTest {

	@InjectMocks
	private ImpostService service;
	@Mock
	private EntityManager entityManager;
	@Mock
	private QueryObject<Impost> query;
	@Mock
	private ValidateObject validator;
	@Mock
	private Validator valid;
	@Mock
	private JPAQuery<Impost> jpa;

	private QImpost qImpost = QImpost.impost;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void testImpostDetails() {
		Impost impost = buildImpost(1L, "Impost", 10);
		assertThat(impost.getName(), equalTo("Impost"));
		assertThat(impost.getId(), equalTo(1L));
		assertThat(impost.getPercent(), equalTo(new BigDecimal(10)));
	}

	@Test
	public void testInsert() throws Exception {
		Impost impost = buildImpost(1L, "Impost", 10);
		when(service.insertImpost(impost)).thenReturn(impost);
		service.insertImpost(impost);
		verify(query, Mockito.times(1)).save(impost);
	}

	@Test
	public void listAll() {
		List<Impost> list = Arrays.asList(buildImpost(1L, "Impost1", 20), buildImpost(2L, "Impost2", 10),
				buildImpost(3L, "Impost3", 5));
		when(query.listAll(qImpost)).thenReturn(list);
		List<Impost> impostList = service.listAll();
		assertThat(impostList.size(), equalTo(3));
		assertThat(impostList.get(0).getId(), equalTo(1L));
		assertThat(impostList.get(1).getId(), equalTo(2L));
		assertThat(impostList.get(2).getId(), equalTo(3L));
	}

	@Test
	public void testFindById() {
		Impost impost = buildImpost(1L, "Impost", 10);
		when(query.findById(qImpost, qImpost.id.eq(1L))).thenReturn(impost);
		Impost returnedImpost = service.findById(1L);
		assertThat(returnedImpost.getId(), equalTo(1L));
	}

	@Test
	public void edit() throws Exception {
		Impost impost = buildImpost(1L, "Impost", 10);
		when(query.editEntity(impost)).thenReturn(buildImpost(1L, "Other", 20));
		Impost otherImpost = service.edit(impost);
		verify(validator, timeout(1)).validateObject(impost);
		verify(query, timeout(1)).editEntity(impost);
		assertThat(otherImpost.getName(), equalTo("Other"));
	}

	@Test
	public void delete() {
		Impost impost = buildImpost(1L, "Impost", 10);
		service.delete(impost.getId());
		verify(query).delete(qImpost, qImpost.id.eq(impost.getId()));
	}

	public Impost buildImpost(long id, String name, int percent) {
		return Impost.builder().id(id).name(name).percent(new BigDecimal(percent)).build();
	}

}
