package ru.mmtr.dictionarySpring.validator;

import ru.mmtr.dictionarySpring.configuration.DictionaryType;

/**
 * Класс ответственен за проверку вводимых данных пользователем
 */
public interface Validator {
    boolean isValidPair(String key, String value, DictionaryType dictionaryType);
}
