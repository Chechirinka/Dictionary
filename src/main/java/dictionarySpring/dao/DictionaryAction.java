package dictionarySpring.dao;


import dictionarySpring.model.dictionaryType.DictionaryType;
import dictionarySpring.model.modelDefault.DictionaryLine;

import java.util.List;

/**
 * Интерфейс предоставляющий метододы для работы с данными словаря
 */
public interface DictionaryAction {

    List<DictionaryLine> read(String selectedDictionaryFrom, String selectedDictionaryTo);

    boolean add(String key, String value, String selectedDictionaryFrom, String selectedDictionaryTo);

    boolean remove(String key, String selectedDictionaryFrom, String selectedDictionaryTo);

    DictionaryLine search(String key, String selectedDictionaryFrom, String selectedDictionaryTo);

}
