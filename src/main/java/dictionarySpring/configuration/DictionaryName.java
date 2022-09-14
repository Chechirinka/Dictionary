package dictionarySpring.configuration;

import dictionarySpring.exception.TypeNotFoundException;

/**
 * Перечисление, которое отвечает за хранение типов словарей
 */
public enum DictionaryName {
    ENGLISH(1, "^[a-zA-Z]{4}$", "[a-zA-Z]+", "src/main/resources/DictionaryE.txt"),
    DIGITAL(2, "^[0-9]{5}$", "[a-zA-Z]+", "src/main/resources/DictionaryD.txt");

    private static final String LANGUAGE_NOT_EXIST = "Ошибка, такого словаря нет";
    private final Integer dictionaryKey;
    private final String patternKey;
    private final String patternValue;
    private final String path;

    DictionaryName(Integer dictionaryKey, String patternKey, String patternValue, String path) {
        this.dictionaryKey = dictionaryKey;
        this.patternKey = patternKey;
        this.patternValue = patternValue;
        this.path = path;
    }

    public static DictionaryName getDictionaryTypeByNumber(Integer number) throws TypeNotFoundException {
        for (DictionaryName dictionaryName : DictionaryName.values()) {
            if (dictionaryName.getDictionaryKey().equals(number)) {
                return dictionaryName;
            }
        }
        throw new TypeNotFoundException(LANGUAGE_NOT_EXIST);
    }

    public Integer getDictionaryKey() {
        return dictionaryKey;
    }

    public String getPatternKey() {
        return patternKey;
    }

    public String getPatternValue() {
        return patternValue;
    }

    public String getPath() {
        return path;
    }
}



