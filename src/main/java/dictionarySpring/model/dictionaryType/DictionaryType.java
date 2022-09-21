package dictionarySpring.model.dictionaryType;

public class DictionaryType {

    private final String pattern;
    private final String path;


    public DictionaryType(String pattern, String path) {
        this.pattern = pattern;
        this.path = path;
    }

    public String getPattern() {
        return pattern;
    }

    public String getPath() {
        return path;
    }
}
