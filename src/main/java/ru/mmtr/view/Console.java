package ru.mmtr.view;

import java.util.Scanner;
import ru.mmtr.service.DictionaryException;
import ru.mmtr.service.DictionaryService;

public class Console {

    private DictionaryService dictionaryService;

    public void choice(String selection){
        System.out.println("Select lang: 1 - English; 2 - Digital;");
        try{
            dictionaryService = new DictionaryService(selection, in.nextInt());
        }
        catch(DictionaryException dictionaryException) {
            System.err.println(dictionaryException.getMessage());
        }
    }
    
    Scanner in = new Scanner(System.in);

    public void actions() {
        boolean a = true;
        while (a) {
            System.out.println("Enter action: 1-add; 2 - read; 3 - remove; 4 - search; 5-exit");
            int inaction = in.nextInt();
            switch (inaction) {
                case 1:
                    System.out.println("Enter key");
                    String key = in.next();
                    System.out.println("Enter value");
                    String value = in.next();
                    System.out.println(dictionaryService.addService(key, value));
                    break;
                case 2:
                    System.out.println(dictionaryService.readService());
                    break;
                case 3:
                    System.out.println("Enter key");
                    key = in.next();
                    dictionaryService.removeService(key);
                    break;
                case 4:
                    System.out.println("Enter key");
                    key = in.next();
                    System.out.println(dictionaryService.searchService(key));
                    break;
                case 5:
                    a = false;
            }
        }
    }
}


