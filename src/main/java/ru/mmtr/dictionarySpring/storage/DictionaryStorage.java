package ru.mmtr.dictionarySpring.storage;

import ru.mmtr.dictionarySpring.configuration.DictionaryName;
import ru.mmtr.dictionarySpring.model.DictionaryLine;

import java.util.List;

/**
 * Интерфейс предоставляющий метододы для работы с данными словаря
 */
public interface DictionaryStorage {

    /**
     * Метод, который отвечает за чтение данных из файла
     *
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return List<DictionaryLine> - возвращает список обьектов типа DictionaryLine
     */
    List<DictionaryLine> read(DictionaryName selectedDictionary);

    /**
     * Метод, который отвечает за добавление данных в файл
     *
     * @param key                - ключ
     * @param value              - значение
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return логическое значение
     */
    boolean add(String key, String value, DictionaryName selectedDictionary);

    /***
     * Метод, который отвечает за удаление данных из файла
     * @param key - ключ
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return логическое значение
     */
    boolean remove(String key, DictionaryName selectedDictionary);

    /**
     * Метод, который отвечает за поиск данных в файле
     *
     * @param key                - ключ
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return DictionaryLine - возвращает обьект типа DictionaryLine
     */
    DictionaryLine search(String key, DictionaryName selectedDictionary);

}
