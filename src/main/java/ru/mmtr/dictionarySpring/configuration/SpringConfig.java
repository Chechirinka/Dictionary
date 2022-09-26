package ru.mmtr.dictionarySpring.configuration;

import ru.mmtr.dictionarySpring.storage.DictionaryStorage;
import ru.mmtr.dictionarySpring.storage.FileStorage;
import ru.mmtr.dictionarySpring.storage.MapStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("ru.mmtr.dictionarySpring")
@PropertySource(value = "classpath:properties.yml")
public class SpringConfig {

    private final static String MAP = "map";
    private final static String FILE = "file";

    @Bean(name = "dictionaryFactory")
    public DictionaryStorage getDictionary(@Value("${type}") String args) {
        switch (args) {
            case (MAP):
                return new MapStorage();
            case (FILE):
                return new FileStorage();
        }
        throw new RuntimeException("В настройках не выбран словарь!");
    }
}

