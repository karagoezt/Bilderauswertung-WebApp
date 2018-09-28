package de.meetme.db;


import de.meetme.data.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;


/**
 * Data access object for persons.
 *
 * It is derived from the AbstractDao using its base database operations.
 *
 * Additionally it has byName() method which allows to search for a special person.
 */
public class UserDao extends AbstractDao<User> {

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * This is a specialized database operation to search for a specific person in the database.
     * @param name
     * @return
     */
    public List<User> byName(String name) {
        String sqlQuery = "select * from " + getEntityClass().getSimpleName() + " where name = ?";
        Query q = currentSession().createNativeQuery(sqlQuery, User.class);
        q.setParameter( 1, name );
        return q.<User>getResultList();
    }

    /**
     * Liefert den Benutzer zurück durch Abgleichen der Email
     * @param email
     * @return
     */
    public List<User> byEmail(String email) {
        String sqlQuery = "select * from " + getEntityClass().getSimpleName() + " where email = ?";
        Query q = currentSession().createNativeQuery(sqlQuery, User.class);
        q.setParameter( 1, email );
        return q.<User>getResultList();
    }

    public List<User> createPerson(User user) {
        String sqlQuery = "Insert Into "+ getEntityClass().getSimpleName() +" (firstname, name, email, password) values (?,?,?,?)";
        Query q = currentSession().createNativeQuery(sqlQuery, User.class);
        q.setParameter( 1, user.getFirstName() );
        q.setParameter( 2, user.getLastname() );
        q.setParameter( 3, user.getEmail() );
        q.setParameter( 4, user.getPassword() );
        return q.<User>getResultList();
    }

}