package dictionarySpring.validator;


import dictionarySpring.model.dictionaryType.DictionaryType;

/**
 * Класс ответственен за проверку вводимых данных пользователем
 */
public interface Validator {
    boolean isValidPair(String key, String value, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo);
}
