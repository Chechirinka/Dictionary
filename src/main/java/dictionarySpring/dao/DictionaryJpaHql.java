package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.database.Dictionaries;
import dictionarySpring.model.modelDefault.DictionaryLine;
import dictionarySpring.storage.DictionaryStorage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class DictionaryJpaHql implements DictionaryStorage {

    private final SessionFactory sessionFactory;


    public DictionaryJpaHql(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictionaryLine> read(DictionaryType selectedDictionary) {

        Session session = sessionFactory.openSession();

        List<Dictionaries> dictionaryLines = session.createQuery("select d from Dictionaries d", Dictionaries.class)
                .getResultList();

        session.close();

        List<DictionaryLine> result = new ArrayList<>();

        dictionaryLines.forEach(x -> {
            result.add(new DictionaryLine(x.getKeys().getWord(), x.getValues().getWord()));
        });
        return result;
    }

    @Override
    @Transactional
    public boolean addTo(String key, String value, DictionaryType selectedDictionary) {
        return false;
    }

    @Override
    public boolean remove(String key, DictionaryType selectedDictionary) {
        Session session = sessionFactory.openSession();

        Dictionaries value = session.createQuery("FROM Dictionaries WHERE keys.word = :key " +
                        "AND keys.lan.name =: from " +
                        "AND values.lan.name =: to", Dictionaries.class)
                .setParameter("key", key)
                .setParameter("from", selectedDictionary.getFrom())
                .setParameter("to", selectedDictionary.getTo())
                .getSingleResult();
        session.delete(value);
        return true;
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
