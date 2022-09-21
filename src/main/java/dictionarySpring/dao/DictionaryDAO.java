package dictionarySpring.dao;

import dictionarySpring.model.dictionaryType.DictionaryType;
import dictionarySpring.model.modelDefault.DictionaryLine;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DictionaryDAO implements DictionaryAction {

    private JdbcTemplate jdbcTemplate;

    public DictionaryDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SEARCH = "SELECT key, value " +
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

    private static final String READ = "SELECT key, value " +
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

    private static final String DELETING = "DELETE FROM dictionaries " +
            "WHERE keys = (SELECT id FROM words WHERE word = ?) or values = (SELECT id FROM words WHERE word = ?); " +
            "DELETE FROM words WHERE word = ?";

    private static final String ADDING = "insert into words(word, lan_id) " +
            "values (?, (select id FROM languages where name = ?)); " +
            "insert into words(word, lan_id) " +
            "values (?, (select id FROM languages where name = ?)); ";

    @Override
    public List<DictionaryLine> read(String selectedDictionaryFrom, String selectedDictionaryTo) {
        return jdbcTemplate.query(READ, new Object[]{selectedDictionaryFrom, selectedDictionaryTo}, new BeanPropertyRowMapper<>(DictionaryLine.class));
    }

    @Override
    public boolean add(String key, String value, String selectedDictionaryFrom, String selectedDictionaryTo) {
        jdbcTemplate.update(ADDING, key, selectedDictionaryFrom, value, selectedDictionaryTo);
        return true;
    }

    @Override
    public boolean remove(String key, String selectedDictionaryFrom, String selectedDictionaryTo) {
        jdbcTemplate.update(DELETING, key, selectedDictionaryFrom, selectedDictionaryTo);
        return true;
    }

    @Override
    public DictionaryLine search(String key, String selectedDictionaryFrom, String selectedDictionaryTo) {
        List<DictionaryLine> searchLines = jdbcTemplate.query(SEARCH, new Object[]{key, selectedDictionaryFrom, selectedDictionaryTo}, new BeanPropertyRowMapper<>(DictionaryLine.class));
        for (DictionaryLine searchLine : searchLines) {
            if (key.equals(searchLine.getKey())) {
                return searchLine;
            }
        }
        return null;
    }
}