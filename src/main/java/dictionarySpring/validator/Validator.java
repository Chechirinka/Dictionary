package dictionarySpring.validator;

/**
 * Класс ответственен за проверку вводимых данных пользователем
 */
public interface Validator {
    boolean isValidPair(String key, String value, String selectedDictionaryFrom, String selectedDictionaryTo);
}
