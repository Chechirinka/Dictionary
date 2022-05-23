package storage;

import configuration.DictionaryType;

import java.util.List;

public interface DictionaryStorage {
    List<String> read();
    String add(String key, String value);
    void remove(String key) ;
    String search(String key);

}
