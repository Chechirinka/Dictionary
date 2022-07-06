package dictionarySpring.service;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.controllers.Formation;
import dictionarySpring.model.DictionaryLine;
import dictionarySpring.storage.*;
import dictionarySpring.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Класс отвечает за разделение слоя хранения и слоя представления
 */
@Component
public class DictionaryService {

    private final Validator validator;
    private final DictionaryStorage dictionaryStorage;

    public final static String NO_EXIST_KEY = "Key don't found!";

    @Autowired
    private Formation formation;

    @Autowired
    public DictionaryService(Validator validator, DictionaryStorage dictionaryStorage) {
        this.validator = validator;
        this.dictionaryStorage = dictionaryStorage;
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
    public boolean addService(String key, String value, DictionaryType selectedDictionary) {
        if (validator.isValidPair(key, value, selectedDictionary)) {
            return dictionaryStorage.addTo(key, value, selectedDictionary);
        } else {
            return false;
        }
    }

    /**
     * Метод отвечает за обращение к методу чтения данных относительно способа хранения и выбранного языка
     *
     * @param selectedDictionary выбранный язык словаря
     * @return преобразованные строки из хранилища
     */
    public List<String> readService(DictionaryType selectedDictionary) {
        return formation.castToString(dictionaryStorage.read(selectedDictionary));
    }

    /**
     * Метод отвечает за обращение к методу чтения данных относительно способа хранения и выбранного языка
     *
     * @param selectedDictionary выбранный язык словаря
     * @return строки из хранилища
     */
    public List<DictionaryLine> readServiceRest(DictionaryType selectedDictionary) {
        return dictionaryStorage.read(selectedDictionary);
    }

    /**
     * Метод отвечает за оборащение к методу удаление данных относительно способа хранения и выбранного языка
     *
     * @param key                ключ, введенный пользователем
     * @param selectedDictionary выбранный язык словаря
     * @return логическое значение
     */
    public boolean removeService(String key, DictionaryType selectedDictionary) {
        return dictionaryStorage.remove(key, selectedDictionary);
    }

    /**
     * Метод отвечает за обращение к методу поиска значения по ключу и вывод строки относительно способа хранения выбранного языка
     *
     * @param key                ключ, введенный пользователем
     * @param selectedDictionary выбранный язык словаря
     * @return объект типа String
     */
    public String searchService(String key, DictionaryType selectedDictionary)
    {
        final Optional<DictionaryLine> optionalReturn = Optional.ofNullable(dictionaryStorage.search(key, selectedDictionary));
        if (optionalReturn.isEmpty()) {
            return NO_EXIST_KEY;
        }
        else {
            return formation.castToString(dictionaryStorage.search(key, selectedDictionary));
        }
    }

    /**
     * Метод отвечает за обращение к методу поиска значения по ключу и вывод строки относительно способа хранения выбранного языка
     *
     * @param key                ключ, введенный пользователем
     * @param selectedDictionary выбранный язык словаря
     * @return объект типа ResponseEntity<?>
     */
    public ResponseEntity<?> searchServiceRest(String key, DictionaryType selectedDictionary)
    {
        final Optional<DictionaryLine> optionalReturn = Optional.ofNullable(dictionaryStorage.search(key, selectedDictionary));
        if (optionalReturn.isEmpty()) {
            return new ResponseEntity<>(NO_EXIST_KEY, HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(dictionaryStorage.search(key, selectedDictionary), HttpStatus.OK);
        }
    }

}

