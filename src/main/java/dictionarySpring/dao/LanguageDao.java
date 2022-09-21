package dictionarySpring.dao;

import dictionarySpring.model.database.Languages;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LanguageDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public LanguageDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static final String REGULAR = "SELECT pattern " +
            "FROM Languages " +
            "WHERE name = ?";

//    public List<Languages> findLanguages() {
//        return sessionFactory.getCurrentSession().createQuery("select l from Languages l", Languages.class).getResultList();
//    }

@Transactional
    public Languages getFindRegular(String dictionaryRegular) {
        return sessionFactory.getCurrentSession().createQuery("SELECT pattern from Languages where name =: dictionaryRegular", Languages.class)
                .setParameter("dictionaryRegular", dictionaryRegular)
                .getSingleResult();
    }

//    public Languages findRegularForJdbc(String dictionaryRegular) {
//        return sessionFactory.getCurrentSession().createQuery("SELECT pattern from Languages where name =: dictionaryRegular", Languages.class)
//                .setParameter("dictionaryRegular", dictionaryRegular)
//                .getSingleResult();
//    }

//    public String getFindRegular(String dictionaryRegular) {
//        String findRegular =  jdbcTemplate.queryForObject(REGULAR, new Object[] {dictionaryRegular}, String.class);
//        return findRegular;
//    }
}
