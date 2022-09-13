package ru.mmtr.configuration;

public enum DictionaryType {
    DICTIONARY_ONE(1, "^[a-zA-Z]{4}$", "[a-zA-Z]+", "src/main/resources/DictionaryE.txt", "English"),
    DICTIONARY_TWO(2, "^[0-9]{5}$", "[a-zA-Z]+", "src/main/resources/DictionaryD.txt", "Digital");

    private static final String splitChar = ":";
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

    public static String getSymbol() {
        return splitChar;
    }

    public Integer getNumber() {return number;}

    public String getPatternKey() {return patternKey;}

    public String getPatternValue() {return patternValue;}

    public String getDictionaryPath() {
        return dictionaryPath;
    }
}

