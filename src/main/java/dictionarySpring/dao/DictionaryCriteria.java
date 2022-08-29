package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.database.Dictionaries;
import dictionarySpring.model.database.Words;
import dictionarySpring.model.modelDefault.DictionaryLine;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class DictionaryCriteria implements DictionaryStorage {

    private SessionFactory sessionFactory;
    @Autowired
    private LanguageDao languageDao;


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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Words keyWords = new Words(key, languageDao.findLanguages(selectedDictionary.getFrom()));
            sessionFactory.getCurrentSession().saveOrUpdate(keyWords);
            Words valueWords = new Words(value, languageDao.findLanguages(selectedDictionary.getTo()));
            sessionFactory.getCurrentSession().saveOrUpdate(valueWords);
            Dictionaries dictionaries = new Dictionaries(keyWords, valueWords);

            sessionFactory.getCurrentSession().saveOrUpdate(dictionaries);

            session.getTransaction().commit();
            return true;
        } catch (HibernateException hibernateException) {
            return false;
        }
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

        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Dictionaries.class);
        var dictionaries = criteria.from(Dictionaries.class);

        criteria.select(dictionaries).where(
                cb.equal(dictionaries.get("keys").get("word"), key),
                cb.equal(dictionaries.get("keys").get("lan").get("name"), selectedDictionary.getFrom()),
                cb.equal(dictionaries.get("values").get("lan").get("name"), selectedDictionary.getTo()));

        Dictionaries value = session.createQuery(criteria)
                .getSingleResult();

        return new DictionaryLine(value.getKeys().getWord(), value.getValues().getWord());
    }
}
