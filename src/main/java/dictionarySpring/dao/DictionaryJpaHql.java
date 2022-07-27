package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.Dictionaries;
import dictionarySpring.storage.DictionaryStorage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DictionaryJpaHql implements DictionaryStorage {

    private final SessionFactory sessionFactory;


    public DictionaryJpaHql(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dictionaries> read(DictionaryType selectedDictionary) {

        Session session = sessionFactory.openSession();

        List<Dictionaries> dictionaryLines = session.createQuery("select d from Dictionaries d", Dictionaries.class)
                .getResultList();

        session.close();
        return dictionaryLines;
    }

    @Override
    @Transactional
    public boolean addTo(String key, String value, DictionaryType selectedDictionary) {
        return false;
    }

    @Override
    public boolean remove(String key, DictionaryType selectedDictionary) {
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Dictionaries search(String key, DictionaryType selectedDictionary) {
        Session session = sessionFactory.openSession();

        return session.get(Dictionaries.class, key);
    }
}
