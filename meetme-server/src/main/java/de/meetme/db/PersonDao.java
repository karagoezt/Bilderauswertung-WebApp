package de.meetme.db;


import de.meetme.data.Person;
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
public class PersonDao extends AbstractDao<Person> {

    public PersonDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * This is a specialized database operation to search for a specific person in the database.
     * @param name
     * @return
     */
    public List<de.meetme.data.Person> byName(String name) {
        String sqlQuery = "select * from " + getEntityClass().getSimpleName() + " where name = ?";
        Query q = currentSession().createNativeQuery(sqlQuery, de.meetme.data.Person.class);
        q.setParameter( 1, name );
        return q.<de.meetme.data.Person>getResultList();
    }

}