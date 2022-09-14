package dictionarySpring.validator;

import dictionarySpring.configuration.DictionaryName;

/**
 * Класс ответственен за проверку вводимых данных пользователем
 */
public interface Validator {
    boolean isValidPair(String key, String value, DictionaryName dictionaryName);
}
