package com.tax.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.tax.entity.Impost;
import com.tax.service.ImpostService;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("impost")
public class ImpostResource {

	@Inject
	private ImpostService service;

	@Path("new")
	@POST
	public Impost createImpost(Impost impost) throws Exception {
		return service.insertImpost(impost);
	}

	@GET
	@Path("all")
	public List<Impost> all() {
		return service.listAll();
	}

	@GET
	@Path("{id}")
	public Impost findById(@PathParam("id") Long id) {
		return service.findById(id);
	}

	@PUT
	@Path("edit")
	public Impost edit(Impost impost) throws Exception {
		return service.edit(impost);
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {
		service.delete(id);
	}

}
