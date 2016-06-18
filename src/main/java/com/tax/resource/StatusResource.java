package com.tax.resource;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("status")
@Consumes(APPLICATION_JSON)
public class StatusResource {

	@GET
	public String status() {
		return "Ok";
	}

}
