package dictionarySpring.storage;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.model.Dictionaries;
import dictionarySpring.service.DictionaryLineCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Класс отвечающий за хранение словаря в файловой системе
 */
public class FileStorage implements DictionaryStorage {

    @Autowired
    private DictionaryLineCodec dictionaryLineCodec;

    private void fileClear(String path, boolean isClear) {
        try {
            new FileWriter(new ClassPathResource(path).getFile(), isClear).close();
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

        File file = new File(path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, UTF_8, isWrite))) {
            Dictionaries dictionaryLine = new Dictionaries(key, value);
            if (!path.isEmpty()) {
                writer.write(dictionaryLineCodec.decode(dictionaryLine) + "\n");
            } else {
                writer.write(dictionaryLineCodec.decode(dictionaryLine));
            }
        }

    }

    /**
     * Метод, который отвечает за доступ к чтению из файла
     *
     * @param path - принимает путь
     * @return - возвращает список <ключ,значение>
     */
    private List<Dictionaries> operationRead(String path) {

        List<Dictionaries> results = new LinkedList<>();
        File file = new File(path);
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
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
     * @param selectedDictionary - принимает вид языка с которым работает
     * @return mapRead - возвращает список пар <ключ, значение>
     */
    @Override
    public List<Dictionaries> read(DictionaryType selectedDictionary) {
        return operationRead(selectedDictionary.getDictionaryPath());
    }

    /**
     * Метод, который отвечает за добавление данных в файл
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
        List<Dictionaries> readLines = operationRead(selectedDictionary.getDictionaryPath());
        for (Dictionaries dictionaryLine : readLines) {
            if (dictionaryLine.getKey().equals(key)) {
                isRemoved = readLines.remove(dictionaryLine);
                break;
            }
        }
        if (!isRemoved) {
            return false;
        }
        fileClear(selectedDictionary.getDictionaryPath(), false);
        for (Dictionaries readLine : readLines) {
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
    public Dictionaries search(String key, DictionaryType selectedDictionary){
        List<Dictionaries> searchLines = operationRead(selectedDictionary.getDictionaryPath());
        for (Dictionaries searchLine : searchLines) {
            if (key.equals(searchLine.getKey())) {
                return searchLine;
            }
        }
        return null;
    }
}