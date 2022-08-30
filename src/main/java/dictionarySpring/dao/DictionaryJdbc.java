package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.modelDefault.DictionaryLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DictionaryJdbc implements DictionaryAction {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String ADD_PAIR = "insert into words(word, lan_id) " +
            "values (?, (select id FROM languages where name = ?)); " +
            "insert into words(word, lan_id) " +
            "values (?, (select id FROM languages where name = ?)); ";
    private static final String READ_PAIR = "SELECT key, value " +
            "FROM( " +
            "SELECT dictionaries.id AS m, words.word AS key, dictionaries.keys, languages.name AS lan_first " +
            "FROM " +
            "languages  JOIN words ON languages.id = words.lan_id " +
            "JOIN dictionaries ON words.id = dictionaries.keys " +
            ") AS a " +
            "JOIN (SELECT dictionaries.id AS l, words.word AS value, dictionaries.values, languages.name As lan_second " +
            "FROM " +
            "languages  JOIN words ON languages.id = words.lan_id " +
            "JOIN dictionaries ON words.id = dictionaries.values) AS c " +
            "ON a.m = c.l " +
            "WHERE lan_first = ? AND lan_second = ? ";
    private static final String SEARCH_PAIR = "SELECT key, value " +
            "FROM( " +
            "SELECT dictionaries.id AS m, words.word AS key, dictionaries.keys, languages.name AS lan_first " +
            "FROM " +
            "languages  JOIN words ON languages.id = words.lan_id " +
            "JOIN dictionaries ON words.id = dictionaries.keys " +
            ") AS a " +
            "JOIN (SELECT dictionaries.id AS l, words.word AS value, dictionaries.values, languages.name As lan_second " +
            "FROM " +
            "languages  JOIN words ON languages.id = words.lan_id " +
            "JOIN dictionaries ON words.id = dictionaries.values) AS c " +
            "ON a.m = c.l " +
            "WHERE key = ? AND lan_first = ? AND lan_second = ? ";
    private static final String DELETE_PAIR = "DELETE FROM dictionaries " +
            "WHERE keys = (SELECT id FROM words WHERE word = ?) or values = (SELECT id FROM words WHERE word = ?); " +
            "DELETE FROM words WHERE word = ?";

    @Override
    public boolean addTo(String key, String value, DictionaryType selectedDictionary) {
        jdbcTemplate.update(ADD_PAIR, key, selectedDictionary.getFrom(), value, selectedDictionary.getTo());
        return true;
    }

    @Override
    public List<DictionaryLine> read(DictionaryType selectedDictionary) {
        return jdbcTemplate.query(READ_PAIR, new Object[]{selectedDictionary.getFrom(), selectedDictionary.getTo()},
                new BeanPropertyRowMapper<>(DictionaryLine.class));
    }

    @Override
    public DictionaryLine search(String key, DictionaryType selectedDictionary) {
        List<DictionaryLine> searchLines = jdbcTemplate.query(SEARCH_PAIR, new Object[]{key, selectedDictionary.getFrom(), selectedDictionary.getTo()}, new BeanPropertyRowMapper<>(DictionaryLine.class));
        for (DictionaryLine searchLine : searchLines) {
            if (key.equals(searchLine.getKey())) {
                return searchLine;
            }
        }
        return null;
    }

    @Override
    public boolean remove(String key, DictionaryType selectedDictionary) {
        jdbcTemplate.update(DELETE_PAIR, key, key, key);
        return true;
    }
}