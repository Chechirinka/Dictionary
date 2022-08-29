package dictionarySpring.dao;

import dictionarySpring.model.database.Languages;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LanguageDao {

    private SessionFactory sessionFactory;

    @Autowired
    public LanguageDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Languages findLanguages(String dictionary) {
        return sessionFactory.getCurrentSession().createQuery("from Languages WHERE name  =: dictionary  ", Languages.class)
                .setParameter("dictionary", dictionary)
        .getSingleResult();
    }
}
