package controller;

import configuration.DictionaryType;

import validator.ValidInterface;
import validator.Validation;

import java.util.*;

public class WorkWithMap implements Dictionary {

    private static final String ADD_KEY = "added";
    private static final String SIMILARITY_TO_THE_PATTERN = "erorr";
    public static final String NO_KEY = "No key found!";
    public static final String KEY_DOES_NOT_EXIST = "This key does not exist!";

   private ValidInterface validInterface;


    public static Map<String, String> map = new HashMap<>();

    @Override
    public List<String> read() {
        List<String> mapRead = new ArrayList<>();
        for (String mapper : map.keySet()) {
            mapRead.add(mapper + DictionaryType.getSymbol() + map.get(mapper));
        }
        return mapRead;
    }

    @Override
    public String add(String key, String value) {
        if (validInterface.isValidPair(key, value)) {
            map.put(key, value);
            return ADD_KEY;
        } else {
            return SIMILARITY_TO_THE_PATTERN;
        }
    }

    @Override
    public void remove(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
        } else {
            try {
                throw new Exception(NO_KEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String search(String key) {
        String search = map.get(key);
        if (search != null) {
            String searchResult = key + DictionaryType.getSymbol() + search;
            return searchResult;
        } else {
            return KEY_DOES_NOT_EXIST;
        }
    }

    @Override
    public void setDictionaryType(DictionaryType dictionaryType) {
        validInterface = new Validation(dictionaryType.getPatternValue(), dictionaryType.getPatternKey());
    }
}




