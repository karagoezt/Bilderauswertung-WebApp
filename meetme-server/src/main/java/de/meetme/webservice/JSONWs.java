package de.meetme.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class JSONWs {

  public JSONWs() {
  }

  @GET
  @Path("/tojson/{name}")
  public Map getFoo(@PathParam("name") String name) {
    Map m = new HashMap();
    m.put("a", 1);
    m.put("b", 2);
    m.put("name", name);
    return m;
  }
}
