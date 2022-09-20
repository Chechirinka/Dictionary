package dictionarySpring.dao;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.modelDefault.DictionaryLine;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс отвечающий за хранение словаря в оперативной памяти
 */
public class MapAction implements DictionaryAction {

    private Map<String, DictionaryLine> map = new HashMap<>();

    /**
     * Метод, который отвечает за чтение данных из мапы
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return mapRead - возвращает список пар <Ключ, Значение>
     */

    @Override
    public List<DictionaryLine> read(DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo) {

        return new ArrayList<>(map.values());
    }

    /**
     * Метод, который отвечает за добавление данных в мапу
     * @param key                - ключ
     * @param value              - значение
     * @return mapRead - возвращает список пар <Ключ, Значение>
     */
    @Override
    public boolean add(String key, String value, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo) {
        map.put(key, new DictionaryLine(key, value));
        return true;
    }

    /**
     * Метод, который отвечает за удаление данных из мапы
     * @param key                - ключ
     * @return mapRead - возвращает список пар <Ключ, Значение>
     */
    @Override
    public boolean remove(String key, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo) {

        return map.remove(key) != null;
    }

    /**
     * Метод, который отвечает за поиск данных в мапе
     * @param key                - ключ
     * @return mapRead - возвращает список пар <Ключ, Значение>
     */
    @Override
    public DictionaryLine search(String key, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo) {
        return map.get(key);
    }
}