package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.modelDefault.DictionaryLine;

import java.util.List;

/**
 * Интерфейс предоставляющий метододы для работы с данными словаря
 */
public interface DictionaryAction {

    List<DictionaryLine> read(DictionaryType selectedDictionary);

    boolean addTo(String key, String value, DictionaryType selectedDictionary);

    boolean remove(String key, DictionaryType selectedDictionary);

    DictionaryLine search(String key, DictionaryType selectedDictionary);

}
