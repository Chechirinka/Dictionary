package dictionarySpring.model;

public class DictionaryLine {

    public DictionaryLine() {
    }

    private String key;
    private String value;

    public DictionaryLine(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
