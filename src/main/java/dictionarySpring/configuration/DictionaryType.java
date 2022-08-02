package dictionarySpring.configuration;

import dictionarySpring.exception.TypeNotFoundException;

/**
 * Перечисление, которое отвечает за хранение типов словарей
 */
public enum DictionaryType {
    DICTIONARY_ONE(1, "^[a-zA-Z]{4}$", "[a-zA-Z]+", "E:\\DictionaryE.txt", "English", "Russian"),
    DICTIONARY_TWO(2, "^[0-9]{5}$", "[a-zA-Z]+", "E:\\DictionaryD.txt", "English", "Digital");

    private static final String LANGUAGE_NOT_EXIST = "Ошибка, такого словаря нет";
    private final Integer number;
    private final String from;
    private final String to;
    private final String patternKey;
    private final String patternValue;
    private final String dictionaryPath;

    DictionaryType(Integer number, String patternKey, String patternValue, String dictionaryPath, String to, String from) {
        this.number = number;
        this.to = to;
        this.from = from;
        this.patternKey = patternKey;
        this.patternValue = patternValue;
        this.dictionaryPath = dictionaryPath;
    }

    public static DictionaryType getDictionaryTypeByNumber(Integer number) throws TypeNotFoundException {
        for (DictionaryType dictionaryType : DictionaryType.values()) {
            if (dictionaryType.getNumber().equals(number)) {
                return dictionaryType;
            }
        }
        throw new TypeNotFoundException(LANGUAGE_NOT_EXIST);
    }

    public Integer getNumber() {
        return number;
    }

    public String getPatternKey() {
        return patternKey;
    }

    public String getPatternValue() {
        return patternValue;
    }

    public String getDictionaryPath() {
        return dictionaryPath;
    }

    public String getFrom() {return from;}

    public String getTo() {return to;}
}



