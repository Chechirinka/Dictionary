package dictionarySpring.dao;

import dictionarySpring.model.database.Languages;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class LanguageDao {

    private SessionFactory sessionFactory;

    @Autowired
    public LanguageDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Languages> findLanguages() {
        return sessionFactory.getCurrentSession().createQuery("from Languages", Languages.class).getResultList();
    }
}
