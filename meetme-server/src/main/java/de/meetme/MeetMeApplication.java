package de.meetme;

import de.meetme.api.UserService;
import de.meetme.data.User;
import de.meetme.db.UserDao;
import de.meetme.webservice.JSONWs;
import de.meetme.webservice.PlainWs;
import de.meetme.webservice.WSHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class MeetMeApplication extends Application<MeetMeConfiguration> {
    private static final Logger log = LoggerFactory.getLogger(MeetMeApplication.class);

    // Add here new data classes in order to register them at hibernate bundle
    private static final Class<?>[] entities = {User.class, de.meetme.data.Photo.class};

    public static void main(String[] args) throws Exception {
        new MeetMeApplication().run(args);
    }

    /**
     * Create
     */
    private final HibernateBundle<MeetMeConfiguration> hibernate = new HibernateBundle<MeetMeConfiguration>(User.class ,entities) {
        @Override
        public DataSourceFactory getDataSourceFactory(MeetMeConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public String getName() {
        return "webService";
    }

    @Override
    public void initialize(Bootstrap<MeetMeConfiguration> bootstrap) {
        log.debug("initialize");

        // register Website
        bootstrap.addBundle(new AssetsBundle("/htmldocs/", "/", "index.html", "static"));
//        bootstrap.addBundle(new AssetsBundle("/htmldocs/css", "/css", null, "static"));
//        bootstrap.addBundle(new AssetsBundle("/htmldocs/js", "/js", null, "static"));

        // register Dropwizard Hibernate bundle
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(MeetMeConfiguration configuration, Environment environment) {
        log.debug("run");

        // add here new data classes, DAOs and services
        final UserDao dao = new UserDao(hibernate.getSessionFactory());
        UserService personService = new UserService(dao);
        environment.jersey().register(personService);

        // register Services for Website
        environment.jersey().register(new JSONWs());
        environment.jersey().register(new PlainWs());
        final WSHealthCheck healthCheck = new WSHealthCheck();
        environment.healthChecks().register("template", healthCheck);

        // start h2 in server mode to connect remotely
        startDbServer(configuration.getDbPort());

        // create initial data in database
        initDb(configuration, environment);
    }

    private void initDb(MeetMeConfiguration configuration, Environment environment) {
        try {
            Connection con = hibernate.getDataSourceFactory(configuration).build(environment.metrics(), "DataSource").getConnection();
            if(con.getSchema()!= null){
                log.warn("Database already initalized.");
            }else{
                log.warn("Database not initalized yet. Initalize database!");
            }

//            PreparedStatement stmt = con.prepareStatement("insert into person (firstname, name, email) values ('pvn1', 'pn1', 'email1')");
//            PreparedStatement stmt2 = con.prepareStatement("insert into person (firstname, name, email) values ('Tay', 'tay', 'email@g.de')");
//            stmt.executeUpdate();
//            stmt2.executeUpdate();
//
//            stmt.close();
//            stmt2.close();

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Start H2 db as server.
     * You can connect remotly using this URL:
     * jdbc:h2:tcp://localhost:9095/./data/dbfile
     * User: sa
     * Pwd: <KEEP EMPTY>
     *
     * WARNING: Server is NOT secured. Don't use in production!!!!!
     */
    private void startDbServer(String dbPort) {
        try {
            Server.createTcpServer("-tcpPort", dbPort, "-tcpAllowOthers").start();
            log.warn("WARNING: H2 Server started. Only for testing allowed! Don't start on production system!!!!!");
        } catch (SQLException e) {
            log.error("Could not start db server: " + e);
        }
    }

    private static void stopDbServer(String dbPort) {
        try {
            Server.shutdownTcpServer("tcp://localhost:" + dbPort, "", true, true);
        } catch (SQLException e) {
            log.error("Could not shutdown db server: " + e);
        }
    }

}
