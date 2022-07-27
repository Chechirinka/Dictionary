package dictionarySpring.service;

import dictionarySpring.model.Dictionaries;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Класс отвечает за работу над строкой с помощью разделителя
 */
@Component
public class DictionaryLineCodec {

    @Value("${splitChar}")
    private String splitChar;

    public String getSplitChar() {
        return splitChar;
    }

    /**
     * Метод разбивает заданную строку на совпадения с заданным регулярным выражением
     * @param line полученная строка из FileStorage
     * @return объект DictionaryLine
     */
    public Dictionaries encode(String line) {
        String[] words = line.split(getSplitChar());
        return new Dictionaries(words[0], words[1]);
    }

    /**
     * Метод отвечает за соединение ключа и значения через заданный разделитель
     * @param dictionaryLine объект строк
     * @return строку
     */
    public String decode(Dictionaries dictionaryLine) {
        return dictionaryLine.getKey() + splitChar + dictionaryLine.getValue();
    }
}
