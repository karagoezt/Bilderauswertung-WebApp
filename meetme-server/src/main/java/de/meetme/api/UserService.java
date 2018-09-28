package de.meetme.api;


import de.meetme.data.User;
import de.meetme.db.UserDao;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * The UserService is the class that is called by Dropwizard when the REST API for person is called.
 * In @Path below the identifier for this service is declared that needs to be used in the URL to this REST service.
 */

@Path("/user") // Part of the URL to identify this resource
@Produces(MediaType.APPLICATION_JSON) // data exchange is in JSON
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class); // Logging of error and debug messages

    private final UserDao dao; // UserDao (data access object) will be injected by Dropwizard framework in the constructor

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    @GET // HTTP method: get
    @Path("/login/{email}&&{password}") // the URL path contains the parameter "id" this will be provided to the loginUser() method
    @UnitOfWork //  be transaction aware (This tag automatically creates a database transaction with begin/commit or rollback in case of an error
    public String loginUser(@PathParam("email") String email, @PathParam("password") String password) {
        List<User> users = dao.byEmail(email);
        if(!users.isEmpty()){
            if(users.get(0).getPassword().equals(password)){
                return "true";
            }
        }
        return "false";
    }

    @DELETE  // HTTP method: delete
    @Path("/{id}")  // the URL path contains the parameter "id" this will be provided to the removeUser() method
    @Produces(MediaType.TEXT_PLAIN) // We return plain text, no JSON
    @UnitOfWork  //  be transaction aware (This tag automatically creates a database transaction with begin/commit or rollback in case of an error
    public void removeUser(@PathParam("id") long id) {
        User user = dao.get(id);
        dao.remove(user);
    }

    @GET // HTTP method: get
    @UnitOfWork  //  be transaction aware (This tag automatically creates a database transaction with begin/commit or rollback in case of an error
    public List<User> getUser() {
        return  dao.getAll();
    }

//    @POST // HTTP method post (the parameter is provided as stream from the browser)
//    @Path("/register/{firstname}&&{name}&&{email}&&{password}") // the URL path contains the parameter "id" this will be provided to the loginUser() method
//    @UnitOfWork  //  be transaction aware (This tag automatically creates a database transaction with begin/commit or rollback in case of an error
//    public String registerUser(@PathParam("firstname") String firstname, @PathParam("firstname") String name, @PathParam("firstname") String email, @PathParam("firstname") String password){
//        dao.createPerson(new User(firstname,  name, email,"", password));
//        return "User createt";
//    }

    @POST // HTTP method post (the parameter is provided as stream from the browser)
    @Path("/register") // the URL path contains the parameter "id" this will be provided to the loginUser() method
    @UnitOfWork  //  be transaction aware (This tag automatically creates a database transaction with begin/commit or rollback in case of an error
    public String registerUser(User user){
        dao.createPerson(user);
        return "User createt";
    }

    @PUT // HTTP method put (the parameter is provided as stream from the browser)
    @UnitOfWork  //  be transaction aware (This tag automatically creates a database transaction with begin/commit or rollback in case of an error
    public void updateUser(User user) throws Exception {
        log.debug("Update User: " + user);
        dao.update(user);
    }
}
