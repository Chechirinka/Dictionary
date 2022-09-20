package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
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
    public List<DictionaryLine> read(DictionaryType selectedDictionary) {


        String hqls = "select d from Dictionaries d " +
                "WHERE keys.lan.name =: from " +
                "AND values.lan.name =: to";

        List<Dictionaries> dictionaryLines = sessionFactory.getCurrentSession().createQuery(hqls, Dictionaries.class)
                .setParameter("from", selectedDictionary.getFrom())
                .setParameter("to", selectedDictionary.getTo())
                .getResultList();

        List<DictionaryLine> result = new ArrayList<>();

        dictionaryLines.forEach(x -> {
            result.add(new DictionaryLine(x.getKeys().getWord(), x.getValues().getWord()));
        });
        return result;
    }

    @Override
    @Transactional
    public boolean add(String key, String value, DictionaryType selectedDictionary) {

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
            var cb = session.getCriteriaBuilder();

            var criteria = cb.createQuery(Dictionaries.class);
            var dictionaries = criteria.from(Dictionaries.class);

            criteria.select(dictionaries).where(
                    cb.equal(dictionaries.get("keys").get("word"), key),
                    cb.equal(dictionaries.get("keys").get("lan").get("name"), selectedDictionary.getFrom()),
                    cb.equal(dictionaries.get("values").get("lan").get("name"), selectedDictionary.getTo()));

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
