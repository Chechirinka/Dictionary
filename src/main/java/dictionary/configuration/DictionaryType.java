package dictionary.configuration;

import dictionary.exeption.FileNotExistsException;

/**
 * Перечисление, которое отвечает за хранение типов словарей
 */
public enum DictionaryType {
    DICTIONARY_ONE(1, "^[a-zA-Z]{4}$", "[a-zA-Z]+", "src/main/resources/DictionaryE.txt", "English"),
    DICTIONARY_TWO(2, "^[0-9]{5}$", "[a-zA-Z]+", "src/main/resources/DictionaryD.txt", "Digital");

    private final Integer number;
    private final String patternKey;
    private final String patternValue;
    private final String dictionaryPath;

    DictionaryType(Integer number, String patternKey, String patternValue, String dictionaryPath, String dictionaryName) {
        this.number = number;
        this.patternKey = patternKey;
        this.patternValue = patternValue;
        this.dictionaryPath = dictionaryPath;
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

   public static DictionaryType getDictionaryTypeByNumber(Integer number) throws FileNotExistsException {
        for (DictionaryType dictionaryType : DictionaryType.values()) {
            if (dictionaryType.getNumber().equals(number)) {
                return dictionaryType;
            }
        }  throw new FileNotExistsException("Ошибка, такого словаря нет");
    }
}



