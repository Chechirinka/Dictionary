package dictionarySpring.model.dictionaryType;

public class DictionaryType {

    private final String from;
    private final String to;
    private final String patternKey;
    private final String patternValue;
    private final String dictionaryPath;

    DictionaryType(String from, String to, String patternKey, String patternValue, String dictionaryPath) {
        this.from = from;
        this.to = to;
        this.patternKey = patternKey;
        this.patternValue = patternValue;
        this.dictionaryPath = dictionaryPath;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getPatternKey() {
        return patternKey;
    }

    public String getPatternValue() {
        return patternValue;
    }

    public String getDictionaryPath() {return dictionaryPath;}
}
