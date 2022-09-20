package dictionarySpring.service;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.dao.DictionaryAction;
import dictionarySpring.model.modelDefault.DictionaryLine;
import dictionarySpring.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс отвечает за разделение слоя хранения и слоя представления
 */
@Component
public class DictionaryService implements DictionaryAction{

    private final Validator validator;
    private final DictionaryAction dictionaryAction;

    public final static String NO_EXIST_KEY = "Key don't found!";

    @Autowired
    public DictionaryService(Validator validator, DictionaryAction dictionaryAction) {
        this.validator = validator;
        this.dictionaryAction = dictionaryAction;
    }

    /**
     * Метод отвечает за валидацию введенный данных, и при успехе обращается к методу добавления относительно выбранного способа хранения
     * и выбранного языка
     * и возвращает true, при ошибке валидации возвращает false
     *
     * @param key ключ, введенный пользователем
     * @param value значение, введенный пользователем
     * @param selectedDictionary выбранный язык словаря
     * @return логическое значение
     */
    @Override
    public boolean add(String key, String value, DictionaryType selectedDictionary) {
        if (validator.isValidPair(key, value, selectedDictionary)) {
            return dictionaryAction.add(key, value, selectedDictionary);
        } else {
            return false;
        }
    }

    /**
     * Метод отвечает за обращение к методу чтения данных относительно способа хранения и выбранного языка
     *
     * @param selectedDictionary выбранный язык словаря
     * @return строки из хранилища
     */
    @Override
    public List<DictionaryLine> read(DictionaryType selectedDictionary) {
        return dictionaryAction.read(selectedDictionary);
    }

    /**
     * Метод отвечает за оборащение к методу удаление данных относительно способа хранения и выбранного языка
     *
     * @param key                ключ, введенный пользователем
     * @param selectedDictionary выбранный язык словаря
     * @return логическое значение
     */
    @Override
    public boolean remove(String key, DictionaryType selectedDictionary) {
        return dictionaryAction.remove(key, selectedDictionary);
    }

    /**
     * Метод отвечает за обращение к методу поиска значения по ключу и вывод строки относительно способа хранения выбранного языка
     *
     * @param key                ключ, введенный пользователем
     * @param selectedDictionary выбранный язык словаря
     * @return объект типа DictionaryLine
     */
    @Override
    public DictionaryLine search(String key, DictionaryType selectedDictionary) {
        return dictionaryAction.search(key, selectedDictionary);
    }
}