package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.database.Dictionaries;
import dictionarySpring.model.database.Languages;
import dictionarySpring.model.database.Words;
import dictionarySpring.model.modelDefault.DictionaryLine;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class DictionaryCriteria implements DictionaryStorage{

    private SessionFactory sessionFactory;


    @Autowired
    public DictionaryCriteria(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictionaryLine> read(DictionaryType selectedDictionary) {

        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();

        var criteria = criteriaBuilder.createQuery(Dictionaries.class);

        var dictionaries = criteria.from(Dictionaries.class);

        criteria.select(dictionaries);

        List<Dictionaries> dictionaryLines = sessionFactory.getCurrentSession().createQuery(criteria)
                .getResultList();

        List<DictionaryLine> result = new ArrayList<>();

        dictionaryLines.forEach(x -> {
            result.add(new DictionaryLine(x.getKeys().getWord(), x.getValues().getWord()));
        });

        return result;
    }

    @Override
    @Transactional
    public boolean addTo(String key, String value, DictionaryType selectedDictionary) {

        Words keyWords = new Words(key, new Languages(selectedDictionary.getFrom(), selectedDictionary.getPatternKey()));
        Words valueWords = new Words(value, new Languages(selectedDictionary.getTo(), selectedDictionary.getPatternValue()));
        Dictionaries dictionaries = new Dictionaries(keyWords, valueWords);

        sessionFactory.getCurrentSession().saveOrUpdate(dictionaries);

        return true;

    }

    @Override
    public boolean remove(String key, DictionaryType selectedDictionary) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String hql = " FROM Dictionaries WHERE keys.word = :key " +
                    "AND keys.lan.name =: from " +
                    "AND values.lan.name =: to";

            Dictionaries dictionaries = session.createQuery(hql, Dictionaries.class)
                    .setParameter("key", key)
                    .setParameter("from", selectedDictionary.getFrom())
                    .setParameter("to", selectedDictionary.getTo())
                    .getSingleResult();
            System.out.println(dictionaries.toString());
            session.delete(dictionaries);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException hibernateException) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DictionaryLine search(String key, DictionaryType selectedDictionary) {
        Session session = sessionFactory.openSession();

        Dictionaries value = session.createQuery("FROM Dictionaries WHERE keys.word = :key " +
                        "AND keys.lan.name =: from " +
                        "AND values.lan.name =: to", Dictionaries.class)
                .setParameter("key", key)
                .setParameter("from", selectedDictionary.getFrom())
                .setParameter("to", selectedDictionary.getTo())
                .getSingleResult();

        return new DictionaryLine(value.getKeys().getWord(), value.getValues().getWord());
    }
}
