package ru.mmtr.storage;
import ru.mmtr.configuration.DictionaryName;

import java.util.List;

public class FileStorage implements DictionaryStorage {

    private static final String ADD_KEY = "added";
    private static final String SIMILARITY_TO_THE_PATTERN = "erorr";

    FileReader fileReader;
    public List<String> read() {
        return fileReader.read();
    }

    public FileStorage(FileReader fileReader){
        this.fileReader = fileReader;
    }

    @Override
    public String add(String key, String value) {
        return fileReader.write(key, value);
    }

    @Override
    public void remove(String key) {

        List<String> readLines = fileReader.read();
        for (int i = 0; i < readLines.size(); i++) {
            if (key.equals(readLines.get(i).split(DictionaryName.getSymbol())[0])) {
                readLines.remove(i);
                break;
            }
        }
        fileReader.fileClear();
        for (String readLine : readLines) {
            String[] keyAndValue = readLine.split(DictionaryName.getSymbol());
            fileReader.write(keyAndValue[0], keyAndValue[1]);
        }
    }

    public String search(String key) {
        List<String> searchLines = fileReader.read();
        for (int i = 0; i < searchLines.size(); i++) {
            if (key.equals(searchLines.get(i).split(DictionaryName.getSymbol())[0])) {
                return searchLines.get(i);
            }
        }
        return null;
    }
}