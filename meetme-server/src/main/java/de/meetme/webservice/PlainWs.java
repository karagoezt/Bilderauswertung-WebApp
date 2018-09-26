package de.meetme.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PlainWs {

  public PlainWs() {
  }

  @GET
  @Path("/plain/{name}")
  public String getFoo(@PathParam("name") String name) {
    return name;
  }
}
