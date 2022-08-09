package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.database.Dictionaries;
import dictionarySpring.model.database.Word;
import dictionarySpring.model.modelDefault.DictionaryLine;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class DictionaryJpaHql implements DictionaryStorage {
    
    private SessionFactory sessionFactory;


        @Autowired
    public DictionaryJpaHql(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictionaryLine> read(DictionaryType selectedDictionary) {


        List<Dictionaries> dictionaryLines = sessionFactory.getCurrentSession().createQuery("select d from Dictionaries d", Dictionaries.class)
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

            Word keyWord = new Word(key);
            Word valueWord = new Word(value);
            Dictionaries dictionaries = new Dictionaries(keyWord, valueWord);

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
