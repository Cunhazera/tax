package com.tax.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.tax.entity.Product;
import com.tax.service.ProductService;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("product")
public class ProductResource {

	@Inject
	private ProductService service;

	@POST
	@Path("new")
	public void register(Product product) throws Exception {
		service.insertProduct(product);
	}

	@GET
	@Path("all")
	public List<Product> all() {
		return service.listAll();
	}

	@GET
	@Path("{id}")
	public Product findById(@PathParam("id") Long id) {
		return service.findById(id);
	}

	@PUT
	@Path("edit")
	public Product edit(@NotNull Product product) throws Exception {
		return service.edit(product);
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {
		service.delete(id);
	}

}
