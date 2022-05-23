package view;

import java.util.Scanner;
<<<<<<< Updated upstream

import static configuration.DictionaryType.*;
=======
>>>>>>> Stashed changes


public class Console {

<<<<<<< Updated upstream
    public void choice(){
        System.out.println("Select dictionary:");
        System.out.println(DICTIONARY_ONE.getNumber() + " " +
                DICTIONARY_ONE.getDictionaryName());
        System.out.println(DICTIONARY_TWO.getNumber() + " " +
                DICTIONARY_TWO.getDictionaryName());
        if (in.nextInt()==1){
            dictionary.setDictionaryType(DICTIONARY_ONE);
        }
        else {
            dictionary.setDictionaryType(DICTIONARY_TWO);
        }
=======
    DictionaryService dictionaryService = new DictionaryService();

    private Dictionary dictionary;
    public void choice(String selection){
        System.out.println("Select lang: 1 - English; 2 - Digital;");
        try{
            dictionary = new Dictionary(selection, in.nextInt());
        }
        catch(DictionaryException dictionaryException) {
            System.err.println(dictionaryException.getMessage());
        }
>>>>>>> Stashed changes
    }

    private Dictionary dictionary;

    Scanner in = new Scanner(System.in);

    public Console(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void actions() {
        boolean a = true;
        Dictionary choice = getDictionary();
        while (a) {
            System.out.println("Enter action: 1-add; 2 - read; 3 - remove; 4 - search; 5-exit");
            int inaction = in.nextInt();
            switch (inaction) {
                case 1:
                    System.out.println("Enter key");
                    String key = in.next();
                    System.out.println("Enter value");
                    String value = in.next();
                    System.out.println(choice.add(key, value));
                    break;
                case 2:
                    System.out.println(choice.read());
                    break;
                case 3:
                    System.out.println("Enter key");
                    key = in.next();
                    choice.remove(key);
                    break;
                case 4:
                    System.out.println("Enter key");
                    key = in.next();
                    System.out.println(choice.search(key));
                    break;
                case 5:
                    a = false;
            }
        }
    }
    private Dictionary getDictionary(){
        return dictionary;
    }
}
