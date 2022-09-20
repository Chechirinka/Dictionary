package dictionarySpring.dao;


import dictionarySpring.model.dictionaryType.DictionaryType;
import dictionarySpring.model.modelDefault.DictionaryLine;

import java.util.List;

/**
 * Интерфейс предоставляющий метододы для работы с данными словаря
 */
public interface DictionaryAction {

    List<DictionaryLine> read(DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo);

    boolean add(String key, String value, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo);

    boolean remove(String key, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo);

    DictionaryLine search(String key, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo);

}
