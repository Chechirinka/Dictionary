package dictionarySpring.view;

import dictionarySpring.configuration.DictionaryName;
import dictionarySpring.exception.TypeNotFoundException;
import dictionarySpring.model.DictionaryLine;
import dictionarySpring.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class Console {
    public final static String NO_EXIST_KEY = "Ключ не найден";
    private final static String SELECT_LANGUAGE = "Select lang: 1 - English; 2 - Digital;";
    private final static String SELECT_ACTIONS = "Enter action: 1-add; 2 - read; 3 - remove; 4 - search; 5-exit";
    private final static String ENTER_KEY = "Enter key";
    private final static String ENTER_VALUE = "Enter value";
    private final static String NO_EXIST_LANGUAGE = "Ошибка, такого языка не существует, повторите ввод!";
    private final static String SUCCESS = "Success";
    private final static String ERROR = "Error";
    private final static String DELETE = "Удалено";
    private final static String NO_DELETE = "Не удалено";
    private final static int ACTION_ADD = 1;
    private final static int ACTION_READ = 2;
    private final static int ACTION_REMOVE = 3;
    private final static int ACTION_SEARCH = 4;
    private final static int EXIT = 5;

    Scanner in = new Scanner(System.in);

    private DictionaryService dictionaryService;
    private DictionaryName selectedDictionary;

    @Autowired
    private Formation formation;


    @Autowired
    public Console(DictionaryService dictionaryService) {

        this.dictionaryService = dictionaryService;
    }

    public void menuSelectDictionary() {

        boolean validEntry = false;
        int scan = 0;

        while (!validEntry) {
            System.out.println(SELECT_LANGUAGE);
            try {
                scan = in.nextInt();
                validEntry = true;
            } catch (InputMismatchException e) {
                System.out.println("Entered value is not an integer!");
                in.nextLine();
            }
        }
        try {
            selectedDictionary = DictionaryName.getDictionaryTypeByNumber(scan);
        } catch (TypeNotFoundException e) {
            System.out.println(NO_EXIST_LANGUAGE);
            in.nextInt();
        }
    }

    public void menuSelectActions() {
        boolean isMenuActive = true;
        while (isMenuActive) {
            System.out.println(SELECT_ACTIONS);
            int inaction = in.nextInt();
            switch (inaction) {
                case ACTION_ADD:
                    System.out.println(ENTER_KEY);
                    String key = in.next();
                    System.out.println(ENTER_VALUE);
                    String value = in.next();
                    System.out.println(addPair(key, value, selectedDictionary));
                    break;
                case ACTION_READ:
                    System.out.println(readPair(selectedDictionary));
                    break;
                case ACTION_REMOVE:
                    System.out.println(ENTER_KEY);
                    key = in.next();
                    System.out.println(removePair(key, selectedDictionary));
                    break;
                case ACTION_SEARCH:
                    System.out.println(ENTER_KEY);
                    key = in.next();
                    System.out.println(searchPair(key, selectedDictionary));
                    break;
                case EXIT:
                    isMenuActive = false;
            }
        }
    }

    private String addPair(String key, String value, DictionaryName selectedDictionary) {
        if (dictionaryService.add(key, value, selectedDictionary)) {
            return SUCCESS;
        }
        return ERROR;
    }

    private List<String> readPair(DictionaryName selectedDictionary) {
        return formation.castToString(dictionaryService.read(selectedDictionary));
    }

    private String searchPair(String key, DictionaryName selectedDictionary) {
        
        DictionaryLine line = dictionaryService.search(key, selectedDictionary);
        if (line == null) {
            return formation.castToString(dictionaryService.search(key, selectedDictionary));
        }
        return NO_EXIST_KEY;
    }

    private String removePair(String key, DictionaryName selectedDictionary) {
        if (dictionaryService.remove(key, selectedDictionary)) {
            return DELETE;
        }
        return NO_DELETE;
    }
}



