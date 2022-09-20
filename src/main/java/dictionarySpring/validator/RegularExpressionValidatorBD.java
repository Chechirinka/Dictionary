package dictionarySpring.validator;

import dictionarySpring.model.dictionaryType.DictionaryType;
import dictionarySpring.dao.LanguageDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

public class RegularExpressionValidatorBD implements Validator{

    @Autowired
    private LanguageDao languageDao;

    private boolean isValidKey(String key, String selectedDictionaryFrom) {
        return Pattern.matches(languageDao.findRegular(selectedDictionaryFrom).getPattern(), key);
    }

    private boolean isValidValue(String value, String selectedDictionaryTo){
        return Pattern.matches(languageDao.findRegular(selectedDictionaryTo).getPattern(), value);
    }

    /**
     * Метод проверяет совпадени пары <ключ, значение> выбранному языку
     * @param key
     * @param value
     * @return boolean соответствует ли введенная пара <ключ,значение> выбранному языку
     */
    @Override
    public boolean isValidPair(String key, String value, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo) {
        return isValidKey(key, selectedDictionaryFrom.getFrom()) && isValidValue(value, selectedDictionaryTo.getTo());
    }

}
