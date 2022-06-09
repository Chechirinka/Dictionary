package dictionary.model;

public class DictionaryLine {

    private final String key;
    private final String value;
    public DictionaryLine(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}