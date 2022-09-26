package ru.mmtr.dictionarySpring.storage;

import ru.mmtr.dictionarySpring.configuration.DictionaryName;
import ru.mmtr.dictionarySpring.model.DictionaryLine;
import ru.mmtr.dictionarySpring.service.DictionaryLineCodec;
import org.springframework.beans.factory.annotation.Autowired;

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
            new FileWriter(path, isClear).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String key, String value, String path, boolean isWrite) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, UTF_8, isWrite))) {
            DictionaryLine dictionaryLine = new DictionaryLine(key, value);
            writer.write(dictionaryLineCodec.decode(dictionaryLine) + "\n");
        }
    }

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

    @Override
    public List<DictionaryLine> read(DictionaryName selectedDictionary) {
        return operationRead(selectedDictionary.getPath());
    }

    @Override
    public boolean add(String key, String value, DictionaryName selectedDictionary) {
        try {
            write(key, value, selectedDictionary.getPath(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(String key, DictionaryName selectedDictionary) {
        boolean isRemoved = false;
        List<DictionaryLine> readLines = operationRead(selectedDictionary.getPath());
        for (DictionaryLine dictionaryLine : readLines) {
            if (dictionaryLine.getKey().equals(key)) {
                isRemoved = readLines.remove(dictionaryLine);
            }
        }
        if (!isRemoved) {
            return false;
        }
        fileClear(selectedDictionary.getPath(), false);
        for (DictionaryLine readLine : readLines) {
            try {
                write(readLine.getKey(), readLine.getValue(), selectedDictionary.getPath(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public DictionaryLine search(String key, DictionaryName selectedDictionary){
        List<DictionaryLine> searchLines = operationRead(selectedDictionary.getPath());
        for (DictionaryLine searchLine : searchLines) {
            if (key.equals(searchLine.getKey())) {
                return searchLine;
            }
        }
        return null;
    }
}