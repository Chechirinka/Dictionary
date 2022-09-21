package dictionarySpring.validator;

import dictionarySpring.dao.LanguageDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

public class RegularExpressionValidatorBd implements Validator{

    @Autowired
    private LanguageDao languageDao;

    private boolean isValidKey(String key, String selectedDictionaryFrom) {
        return Pattern.matches(key, languageDao.getFindRegular(selectedDictionaryFrom).getPattern());
    }

    private boolean isValidValue(String value, String selectedDictionaryTo){
        return Pattern.matches(value, languageDao.getFindRegular(selectedDictionaryTo).getPattern());
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
