package dictionarySpring.validator;

import dictionarySpring.configuration.DictionaryName;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Класс ответственен за проверку вводимых данных пользователем
 * на основании регулярного выражения
 */
    @Component
public class RegularExpressionValidator implements Validator {

    private boolean isValidKey(String key, String keyPattern) {
        return Pattern.matches(keyPattern, key);
    }

    private boolean isValidValue(String value, String valuePattern) {
        return Pattern.matches(valuePattern, value);
    }

    /**
     * Метод проверяет совпадени пары <ключ, значение> выбранному языку
     *
     * @param key
     * @param value
     * @param dictionaryName
     * @return boolean соответствует ли введенная пара <ключ,значение> выбранному языку
     */
    @Override
    public boolean isValidPair(String key, String value, DictionaryName dictionaryName) {
        return isValidKey(key, dictionaryName.getPatternKey()) && isValidValue(value, dictionaryName.getPatternValue());
    }
}