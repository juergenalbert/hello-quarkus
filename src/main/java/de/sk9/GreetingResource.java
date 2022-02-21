package de.sk9;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

	@Inject
	GreetingService service;
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/")
    public String hello() {
    	return "Hello Quarkus";
    }
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    public String hello(@PathParam(value = "name") String name) {
    	return service.greeting(name);
    }
}