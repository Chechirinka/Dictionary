package dictionary.storage;

import dictionary.configuration.DictionaryType;
import dictionary.model.DictionaryLine;
import dictionary.service.DictionaryLineCodec;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Класс отвечающий за хранение словаря в файловой системе
 */
public class FileStorage implements DictionaryStorage {

    private DictionaryLineCodec dictionaryLineCodec = new DictionaryLineCodec();

    private void fileClear(String path, boolean isClear) {
        try {
            new FileWriter(path, isClear).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, который отвечает за запись в файл
     *
     * @param key   - ключ
     * @param value - значение
     * @param path  - принимает путь
     */
    private void write(String key, String value, String path, boolean isWrite) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, UTF_8, isWrite))) {
            DictionaryLine dictionaryLine = new DictionaryLine(key, value);
            writer.write(dictionaryLineCodec.decode(dictionaryLine) + "\n");
        }
    }

    /**
     * Метод, который отвечает за доступ к чтению из файла
     *
     * @param path - принимает путь
     * @return - возвращает список <ключ,значение>
     */
    private List<DictionaryLine> operationRead(String path) {

        List<DictionaryLine> results = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                results.add(dictionaryLineCodec.encode(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Метод, который отвечает за чтение данных из файла
     *
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return mapRead - возвращает список пар <ключ, значение>
     */
    @Override
    public List<DictionaryLine> read(DictionaryType selectedDictionary) {
        return operationRead(selectedDictionary.getDictionaryPath());
    }

    /**
     * Метод, который отвечает за добавление данных в файл
     *
     * @param key                - ключ
     * @param value              - значение
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return логическое значение
     */
    @Override
    public boolean addTo(String key, String value, DictionaryType selectedDictionary) {
        try {
            write(key, value, selectedDictionary.getDictionaryPath(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /***
     * Метод, который отвечает за удаление данных из файла
     * @param key - ключ
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return логическое значение
     */
    @Override
    public boolean remove(String key, DictionaryType selectedDictionary) {
        boolean isRemoved = false;
        List<DictionaryLine> readLines = operationRead(selectedDictionary.getDictionaryPath());
        for (DictionaryLine dictionaryLine : readLines) {
            if (dictionaryLine.getKey().equals(key)) {
                isRemoved = readLines.remove(dictionaryLine);
                break;
            }
        }
        if (!isRemoved) {
            return false;
        }
        fileClear(selectedDictionary.getDictionaryPath(), false);
        for (DictionaryLine readLine : readLines) {
            try {
                write(readLine.getKey(), readLine.getValue(), selectedDictionary.getDictionaryPath(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Метод, который отвечает за поиск данных в файле
     *
     * @param key                - ключ
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return mapRead - возвращает список пар <ключ, значение> и если ключ не найден
     * возвращается null
     */

    @Override
    public DictionaryLine search(String key, DictionaryType selectedDictionary){
        List<DictionaryLine> searchLines = operationRead(selectedDictionary.getDictionaryPath());
        for (DictionaryLine searchLine : searchLines) {
            if (key.equals(searchLine.getKey())) {
                return searchLine;
            }
        }
      return null;
    }
}