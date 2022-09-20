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
        return sessionFactory.getCurrentSession().createQuery("select l from Languages l", Languages.class).getResultList();
    }

    public Languages findRegular(String dictionaryRegular) {
        return sessionFactory.getCurrentSession().createQuery("SELECT pattern from Languages where name =: dictionaryRegular", Languages.class)
                .setParameter("dictionaryRegular", dictionaryRegular)
                .getSingleResult();
    }
}
