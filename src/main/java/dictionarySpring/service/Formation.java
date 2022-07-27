package dictionarySpring.service;

import dictionarySpring.model.Dictionaries;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс отвечает за форматирование данных для их представления
 */
    @Component
public class Formation {

    @Value("${splitCharView}")
    private String splitCharView;

    /**
     * Метод отвечает за соединение ключа и значения через заданный разделитель
     * @param dictionaryLine объект строк
     * @return строку
     */
    public String castToString(Dictionaries dictionaryLine) {
        return dictionaryLine.getKey() + splitCharView + dictionaryLine.getValue();
    }

    /**
     * Метод отвечает за декодирования Лист обьектов в Лист строк
     *
     * @param dictionaryLines список объектов строк
     * @return список строк
     */
    public List<String> castToString(List<Dictionaries> dictionaryLines) {
        List<String> lines = new ArrayList<>();
        for (Dictionaries dictionaryLine : dictionaryLines) {
            lines.add(castToString(dictionaryLine));
        }
        return lines;
    }
}
