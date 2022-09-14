package dictionarySpring;

import dictionarySpring.configuration.SpringConfig;
import dictionarySpring.view.Console;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Console test = context.getBean(Console.class);
        test.menuSelectDictionary();
        test.menuSelectActions();
    }
}
