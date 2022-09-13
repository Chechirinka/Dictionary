package dictionarySpring.dto;

import dictionarySpring.service.Formation;
import org.springframework.stereotype.Component;

@Component
public class DictionaryDTO {

    private Formation formation;

    public DictionaryDTO(Formation formation) {
        this.formation = formation;
    }


}
