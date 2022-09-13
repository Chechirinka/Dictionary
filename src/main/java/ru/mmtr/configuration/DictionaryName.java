package ru.mmtr.configuration;

public enum DictionaryName {
    DICTIONARY_ONE(1, "^[a-zA-Z]{4}$", "[a-zA-Z]+", "src/main/resources/DictionaryE.txt"),
    DICTIONARY_TWO(2, "^[0-9]{5}$", "[a-zA-Z]+", "src/main/resources/DictionaryD.txt");

    private static final String splitChar = ":";
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

    public static String getSymbol() {
        return splitChar;
    }

    public Integer getDictionaryKey() {return dictionaryKey;}

    public String getPatternKey() {return patternKey;}

    public String getPatternValue() {return patternValue;}

    public String getPath() {
        return path;
    }
}

