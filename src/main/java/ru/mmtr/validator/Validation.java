package ru.mmtr.validator;

import java.util.regex.Pattern;

public class Validation implements ValidInterface{

    String valuePattern;
    String keyPattern;

    public Validation(String valuePattern, String keyPattern){
        this.valuePattern = valuePattern;
        this.keyPattern = keyPattern;
    }

    @Override
    public boolean isValidKey(String key) {
        return Pattern.matches(keyPattern, key);
    }

    @Override
    public boolean isValidValue(String value) {
        return Pattern.matches(valuePattern, value);
    }

    @Override
    public boolean isValidPair(String key, String value) {
        return isValidKey(key) && isValidValue(value);
    }
}
