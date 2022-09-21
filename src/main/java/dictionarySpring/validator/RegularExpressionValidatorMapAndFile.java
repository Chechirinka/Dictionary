package dictionarySpring.validator;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

/**
 * Класс ответственен за проверку вводимых данных пользователем
 * на основании регулярного выражения
 */
    @Component
public class RegularExpressionValidatorMapAndFile implements Validator {

    private boolean isValidKey(String key, String keyPattern) {
        return Pattern.matches(key, keyPattern);
    }

    private boolean isValidValue(String value, String valuePattern) {
        return Pattern.matches(value, valuePattern);
    }

    /**
     * Метод проверяет совпадени пары <ключ, значение> выбранному языку
     * @param key
     * @param value
     * @return boolean соответствует ли введенная пара <ключ,значение> выбранному языку
     */

    @Override
    public boolean isValidPair(String key, String value, String selectedDictionaryFrom, String selectedDictionaryTo) {
        return isValidKey(key, selectedDictionaryFrom) && isValidValue(value, selectedDictionaryTo);
    }
}
