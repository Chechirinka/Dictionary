package ru.mmtr.dictionarySpring.storage;

import ru.mmtr.dictionarySpring.configuration.DictionaryName;
import ru.mmtr.dictionarySpring.model.DictionaryLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс отвечающий за хранение словаря в оперативной памяти
 */
public class MapStorage implements DictionaryStorage {

    private Map<String, DictionaryLine> map = new HashMap<>();

    @Override
    public List<DictionaryLine> read(DictionaryName selectedDictionary) {

        return new ArrayList<>(map.values());
    }

    @Override
    public boolean add(String key, String value, DictionaryName selectedDictionary) {
        map.put(key, new DictionaryLine(key, value));
        return true;
    }

    @Override
    public boolean remove(String key, DictionaryName selectedDictionary) {

        return map.remove(key) != null;
    }

    @Override
    public DictionaryLine search(String key, DictionaryName selectedDictionary) {
        return map.get(key);
    }
}








