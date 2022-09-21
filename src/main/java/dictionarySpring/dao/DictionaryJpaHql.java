package dictionarySpring.dao;

import dictionarySpring.model.database.Dictionaries;
import dictionarySpring.model.database.Languages;
import dictionarySpring.model.database.Words;
import dictionarySpring.model.modelDefault.DictionaryLine;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class DictionaryJpaHql implements DictionaryAction {

    private SessionFactory sessionFactory;


        @Autowired
    public DictionaryJpaHql(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictionaryLine> read(String selectedDictionaryFrom, String selectedDictionaryTo) {


        String hqls = "select d from Dictionaries d " +
                "WHERE keys.lan.name =: from " +
                "AND values.lan.name =: to";

        List<Dictionaries> dictionaryLines = sessionFactory.getCurrentSession().createQuery(hqls, Dictionaries.class)
                .setParameter("from", selectedDictionaryFrom)
                .setParameter("to", selectedDictionaryTo)
                .getResultList();

        List<DictionaryLine> result = new ArrayList<>();

        dictionaryLines.forEach(x -> {
            result.add(new DictionaryLine(x.getKeys().getWord(), x.getValues().getWord()));
        });
        return result;
    }

    @Override
    @Transactional
    public boolean add(String key, String value, String selectedDictionaryFrom, String selectedDictionaryTo) {

            Words keyWords = new Words(key, new Languages(selectedDictionaryFrom, selectedDictionaryFrom));
            Words valueWords = new Words(value, new Languages(selectedDictionaryTo, selectedDictionaryTo));
            Dictionaries dictionaries = new Dictionaries(keyWords, valueWords);

        sessionFactory.getCurrentSession().saveOrUpdate(dictionaries);

            return true;

    }

    @Override
    public boolean remove(String key, String selectedDictionaryFrom, String selectedDictionaryTo) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var cb = session.getCriteriaBuilder();

            var criteria = cb.createQuery(Dictionaries.class);
            var dictionaries = criteria.from(Dictionaries.class);

            criteria.select(dictionaries).where(
                    cb.equal(dictionaries.get("keys").get("word"), key),
                    cb.equal(dictionaries.get("keys").get("lan").get("name"), selectedDictionaryFrom),
                    cb.equal(dictionaries.get("values").get("lan").get("name"), selectedDictionaryTo));

            Dictionaries value = session.createQuery(criteria)
                    .getSingleResult();

            System.out.println(value.toString());
            session.delete(value);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException hibernateException) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DictionaryLine search(String key, String selectedDictionaryFrom, String selectedDictionaryTo) {
        Session session = sessionFactory.openSession();

        Dictionaries value = session.createQuery("FROM Dictionaries WHERE keys.word = :key " +
                        "AND keys.lan.name =: from " +
                        "AND values.lan.name =: to", Dictionaries.class)
                .setParameter("key", key)
                .setParameter("from", selectedDictionaryFrom)
                .setParameter("to", selectedDictionaryTo)
                .getSingleResult();
        return new DictionaryLine(value.getKeys().getWord(), value.getValues().getWord());
    }

}
