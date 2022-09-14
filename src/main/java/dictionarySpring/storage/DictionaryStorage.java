package dictionarySpring.storage;

import dictionarySpring.configuration.DictionaryName;
import dictionarySpring.model.DictionaryLine;

import java.util.List;

/**
 * Интерфейс предоставляющий метододы для работы с данными словаря
 */
public interface DictionaryStorage {

    List<DictionaryLine> read(DictionaryName selectedDictionary);

    boolean add(String key, String value, DictionaryName selectedDictionary);

    boolean remove(String key, DictionaryName selectedDictionary);

    DictionaryLine search(String key, DictionaryName selectedDictionary);

}
