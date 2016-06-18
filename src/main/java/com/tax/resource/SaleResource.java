package com.tax.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.tax.entity.Sale;
import com.tax.service.SaleService;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("sale")
public class SaleResource {

	@Inject
	private SaleService service;

	@GET
	@Path("all")
	public List<Sale> all() {
		return service.listAllSales();
	}

	@POST
	@Path("new")
	public void newSale(Sale sale) throws Exception {
		service.insertSale(sale);
	}

}
