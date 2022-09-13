package ru.mmtr.validator;

public interface ValidInterface {
    boolean isValidPair(String key, String value);
    boolean isValidKey(String key);
    boolean isValidValue(String value);
}
