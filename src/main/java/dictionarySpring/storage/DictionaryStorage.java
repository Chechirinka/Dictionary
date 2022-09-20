package dictionarySpring.storage;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.DictionaryLine;

import java.util.List;

/**
 * Интерфейс предоставляющий метододы для работы с данными словаря
 */
public interface DictionaryStorage {

    List<DictionaryLine> read(DictionaryType selectedDictionary);

    boolean add(String key, String value, DictionaryType selectedDictionary);

    boolean remove(String key, DictionaryType selectedDictionary);

    DictionaryLine search(String key, DictionaryType selectedDictionary);

}
