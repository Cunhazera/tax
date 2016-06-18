package com.tax.operation.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.tax.entity.Impost;
import com.tax.entity.QImpost;
import com.tax.operation.QueryObject;

public class QueryObjectTest {

	@Spy
	@InjectMocks
	private QueryObject<Impost> queryImpost;
	@Mock
	private EntityManager manager;
	@Mock
	private JPAQuery<Impost> jpaQuery;
	@Mock
	private JPAUpdateClause updateClause;
	@Mock
	private JPADeleteClause deleteClause;

	private QImpost qImpost = QImpost.impost;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSave() throws Exception {
		Impost impost = Impost.builder().name(null).id(1L).percent(new BigDecimal(50)).build();
		queryImpost.save(impost);
		Mockito.verify(manager).persist(impost);
		Mockito.verify(queryImpost).save(impost);
	}

	@Test
	public void listAll() {
		Mockito.when(queryImpost.createQuery()).thenReturn(jpaQuery);
		Mockito.when(jpaQuery.from(qImpost)).thenReturn(jpaQuery);
		Mockito.when(jpaQuery.fetch()).thenReturn(Arrays.asList(buildImpost("Imp", 20), buildImpost("Imposto", 10)));
		List<Impost> list = queryImpost.listAll(qImpost);
		assertThat(list, Matchers.hasSize(2));
		assertThat(list.get(0).getName(), Matchers.equalTo("Imp"));
		assertThat(list.get(1).getName(), Matchers.equalTo("Imposto"));
	}

	@Test
	public void listOne() {
		Predicate predicate = qImpost.id.eq(1L);
		Mockito.when(queryImpost.createQuery()).thenReturn(jpaQuery);
		when(jpaQuery.from(qImpost)).thenReturn(jpaQuery);
		Mockito.when(jpaQuery.where(predicate)).thenReturn(jpaQuery);
		Mockito.when(jpaQuery.fetchOne()).thenReturn(Impost.builder().name("1").percent(new BigDecimal(10)).build());
		Impost impost = queryImpost.findById(qImpost, predicate);
		MatcherAssert.assertThat(impost.getName(), Matchers.is("1"));
	}

	@Test
	public void edit() {
		Impost impost = buildImpost("Impost", 50);
		queryImpost.editEntity(impost);
		Mockito.verify(manager, Mockito.never()).persist(impost);
		verify(manager).merge(impost);
	}

	private Impost buildImpost(String name, int percent) {
		return Impost.builder().id(1L).name(name).percent(new BigDecimal(percent)).build();
	}

}
