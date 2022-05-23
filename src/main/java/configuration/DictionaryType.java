package configuration;

public enum DictionaryType {
    DICTIONARY_ONE( "^[a-zA-Z]{4}$", "[a-zA-Z]+", "src/main/resources/DictionaryE.txt"),
    DICTIONARY_TWO( "^[0-9]{5}$", "[a-zA-Z]+", "src/main/resources/DictionaryD.txt");

    private static final String splitChar = ":";
    private final String patternKey;
    private final String patternValue;
    private final String dictionaryPath;

    DictionaryType(String patternKey, String patternValue, String dictionaryPath) {
        this.patternKey = patternKey;
        this.patternValue = patternValue;
        this.dictionaryPath = dictionaryPath;
    }

    public static String getSymbol() {
        return splitChar;
    }

    public String getPatternKey() {return patternKey;}

    public String getPatternValue() {return patternValue;}

    public String getDictionaryPath() {
        return dictionaryPath;
    }
    }

