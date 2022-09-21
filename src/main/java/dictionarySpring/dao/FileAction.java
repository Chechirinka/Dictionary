package dictionarySpring.dao;

import dictionarySpring.model.dictionaryType.DictionaryType;
import dictionarySpring.model.modelDefault.DictionaryLine;
import dictionarySpring.service.DictionaryLineCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Класс отвечающий за хранение словаря в файловой системе
 */
public class FileAction implements DictionaryAction {

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
     * @param key   - ключ
     * @param value - значение
     * @param path  - принимает путь
     */
    private void write(String key, String value, String path, boolean isWrite) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new ClassPathResource(path).getFile(), UTF_8, isWrite))) {
            DictionaryLine dictionaryLine = new DictionaryLine(key, value);
            if (!path.isEmpty()) {
                writer.write(dictionaryLineCodec.decode(dictionaryLine) + "\n");
            } else {
                writer.write(dictionaryLineCodec.decode(dictionaryLine));
            }
        }

    }

    /**
     * Метод, который отвечает за доступ к чтению из файла
     * @param path - принимает путь
     * @return - возвращает список <ключ,значение>
     */
    private List<DictionaryLine> operationRead(String path) {

        List<DictionaryLine> results = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new ClassPathResource(path).getFile()), StandardCharsets.UTF_8))) {
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
     * @return mapRead - возвращает список пар <ключ, значение>
     */
    @Override
    public List<DictionaryLine> read(String selectedDictionaryFrom, String selectedDictionaryTo) {
        return operationRead(selectedDictionaryFrom);
    }

    /**
     * Метод, который отвечает за добавление данных в файл
     * @param key                - ключ
     * @param value              - значение
     * @return логическое значение
     */
    @Override
    public boolean add(String key, String value, String selectedDictionaryFrom, String selectedDictionaryTo) {
        try {
            write(key, value, selectedDictionaryFrom, true);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /***
     * Метод, который отвечает за удаление данных из файла
     * @param key - ключ
     * @return логическое значение
     */
    @Override
    public boolean remove(String key, String selectedDictionaryFrom, String selectedDictionaryTo) {
        boolean isRemoved = false;
        List<DictionaryLine> readLines = operationRead(selectedDictionaryFrom);
        for (DictionaryLine dictionaryLine : readLines) {
            if (dictionaryLine.getKey().equals(key)) {
                isRemoved = readLines.remove(dictionaryLine);
                break;
            }
        }
        if (!isRemoved) {
            return false;
        }
        fileClear(selectedDictionaryFrom, false);
        for (DictionaryLine readLine : readLines) {
            try {
                write(readLine.getKey(), readLine.getValue(), selectedDictionaryFrom, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Метод, который отвечает за поиск данных в файле
     * @param key                - ключ
     * @return mapRead - возвращает список пар <ключ, значение> и если ключ не найден
     * возвращается null
     */
String path;
    @Override
    public DictionaryLine search(String key, String selectedDictionaryFrom, String selectedDictionaryTo){
        List<DictionaryLine> searchLines = operationRead(selectedDictionaryFrom);
        for (DictionaryLine searchLine : searchLines) {
            if (key.equals(searchLine.getKey())) {
                return searchLine;
            }
        }
        return null;
    }
}