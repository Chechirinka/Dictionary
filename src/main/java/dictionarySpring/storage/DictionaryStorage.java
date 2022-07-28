package dictionarySpring.storage;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.Dictionaries;

import java.util.List;

/**
 * Интерфейс предоставляющий метододы для работы с данными словаря
 */
public interface DictionaryStorage {

    List<Dictionaries> read(DictionaryType selectedDictionary);

    boolean addTo(String key, String value, DictionaryType selectedDictionary);

    boolean remove(String key, DictionaryType selectedDictionary);

    Dictionaries search(String key, DictionaryType selectedDictionary);

}
