package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.modelDefault.DictionaryLine;
import dictionarySpring.storage.DictionaryStorage;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DictionaryDAO implements DictionaryStorage {

    private JdbcTemplate jdbcTemplate;

    public DictionaryDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DictionaryLine> read(DictionaryType selectedDictionary) {
        return jdbcTemplate.query("SELECT * FROM dictline", new BeanPropertyRowMapper<>(DictionaryLine.class));
    }

    @Override
    public boolean addTo(String key, String value, DictionaryType selectedDictionary) {
        jdbcTemplate.update("INSERT INTO dictline(key, value) VALUES(?, ?)", key, value);
        return true;
    }

    @Override
    public boolean remove(String key, DictionaryType selectedDictionary) {
        jdbcTemplate.update("DELETE FROM dictline WHERE key = ?", key);
        return true;
    }

    @Override
    public DictionaryLine search(String key, DictionaryType selectedDictionary) {

        return jdbcTemplate.query("SELECT * FROM dictline WHERE key = ?", new Object[]{key}, new BeanPropertyRowMapper<>(DictionaryLine.class))
                .stream().findAny().orElse(null);
    }
}
