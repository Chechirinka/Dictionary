package ru.mmtr.dictionarySpring.storage;

import ru.mmtr.dictionarySpring.configuration.DictionaryType;
import ru.mmtr.dictionarySpring.model.DictionaryLine;

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
