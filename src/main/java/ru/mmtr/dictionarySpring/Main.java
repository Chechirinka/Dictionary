package ru.mmtr.dictionarySpring;

import ru.mmtr.dictionarySpring.configuration.SpringConfig;
import ru.mmtr.dictionarySpring.view.Console;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Console console = context.getBean(Console.class);
        console.menuSelectDictionary();
        console.menuSelectActions();
    }
}
