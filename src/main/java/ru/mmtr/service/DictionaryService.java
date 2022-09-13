package ru.mmtr.service;

import ru.mmtr.configuration.DictionaryName;
import ru.mmtr.storage.*;
import ru.mmtr.validator.ValidInterface;
import ru.mmtr.validator.Validation;

import java.util.List;

public class DictionaryService implements DictionaryStorage{

    private DictionaryName dictionaryName;
    private final ValidInterface validInterface;
    private final DictionaryStorage dictionaryStorage;

    public DictionaryService(String type, int dictionary) throws DictionaryException{
        for (DictionaryName dictionaryName : DictionaryName.values()) {
            if (dictionaryName.getDictionaryKey() == dictionary) {
                this.dictionaryName = dictionaryName;
            }
        }
        if(dictionaryName ==null){
            throw new DictionaryException("Словарь не найден");
        }
        validInterface = new Validation(dictionaryName.getPatternValue(), dictionaryName.getPatternKey());
        if (type.equals("map")) {
            dictionaryStorage = new MapStorage();
        } else {
            dictionaryStorage = new FileStorage(new FileReader(dictionaryName.getPath()));
        }
    }
    @Override
    public String add(String key, String value) {
        if (validInterface.isValidPair(key, value)) {
            return dictionaryStorage.add(key, value);
        } else {
            return "Error";
        }
    }

    @Override
    public List<String> read() {
        return dictionaryStorage.read();
    }

    @Override
    public void remove(String key) {
        dictionaryStorage.remove(key);
    }

    @Override
    public String search(String key) {
        return dictionaryStorage.search(key);
    }

    }
