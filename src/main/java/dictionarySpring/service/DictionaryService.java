package dictionarySpring.service;

import dictionarySpring.dao.DictionaryAction;
import dictionarySpring.model.dictionaryType.DictionaryType;
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

    @Autowired
    public DictionaryService(Validator validator, DictionaryAction dictionaryAction) {
        this.validator = validator;
        this.dictionaryAction = dictionaryAction;
    }

    /**
     * Метод отвечает за валидацию введенный данных, и при успехе обращается к методу добавления относительно выбранного способа хранения
     * и выбранного языка
     * и возвращает true, при ошибке валидации возвращает false
     * @param key ключ, введенный пользователем
     * @param value значение, введенный пользователем
     * @return логическое значение
     */
    @Override
    public boolean add(String key, String value, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo) {
        if (validator.isValidPair(key, value, selectedDictionaryFrom, selectedDictionaryTo)) {
            return dictionaryAction.add(key, value, selectedDictionaryFrom, selectedDictionaryTo);
        } else {
            return false;
        }
    }

    /**
     * Метод отвечает за обращение к методу чтения данных относительно способа хранения и выбранного языка
     * @return строки из хранилища
     */
    @Override
    public List<DictionaryLine> read(DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo) {
        return dictionaryAction.read(selectedDictionaryFrom, selectedDictionaryTo);
    }

    /**
     * Метод отвечает за оборащение к методу удаление данных относительно способа хранения и выбранного языка
     * @param key                ключ, введенный пользователем
     * @return логическое значение
     */
    @Override
    public boolean remove(String key, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo) {
        return dictionaryAction.remove(key, selectedDictionaryFrom, selectedDictionaryTo);
    }

    /**
     * Метод отвечает за обращение к методу поиска значения по ключу и вывод строки относительно способа хранения выбранного языка
     * @param key                ключ, введенный пользователем
     * @return объект типа DictionaryLine
     */
    @Override
    public DictionaryLine search(String key, DictionaryType selectedDictionaryFrom, DictionaryType selectedDictionaryTo) {
        return dictionaryAction.search(key, selectedDictionaryFrom, selectedDictionaryTo);
    }
}