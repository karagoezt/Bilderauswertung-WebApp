package com.dhbw.ws;

import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;


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
