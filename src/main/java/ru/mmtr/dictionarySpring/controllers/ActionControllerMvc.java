package ru.mmtr.dictionarySpring.controllers;

import org.springframework.web.servlet.ModelAndView;
import ru.mmtr.dictionarySpring.configuration.DictionaryType;
import ru.mmtr.dictionarySpring.exception.TypeNotFoundException;
import ru.mmtr.dictionarySpring.model.DictionaryLine;
import ru.mmtr.dictionarySpring.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/action")

public class ActionControllerMvc {

    private final static String ERROR_LANGUAGE = "errorResult";
    private final static String NO_EXIST_LANGUAGE = "Ошибка, такого языка не существует, повторите ввод!";
    private final static String SUCCESS = "Success";
    private final static String ERROR = "Error";
    private final static String DELETE = "Удалено";
    private final static String NO_DELETE = "Не удалено";
    private final static String RESULT = "result";
    public final static String ID = "dictionaryId";

    private final DictionaryService dictionaryService;

    private DictionaryType selectedDictionary;

    @Autowired
    public ActionControllerMvc(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PostMapping("/add")
    public ModelAndView add(@RequestParam(value = "key") String key,
                      @RequestParam(value = "value") String value,
                      @RequestParam(value = "dictionaryId") int dictionaryId, ModelAndView modelAndView) {
        modelAndView.addObject(ID, dictionaryId);
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            modelAndView.addObject(ERROR_LANGUAGE, NO_EXIST_LANGUAGE);
        }
        modelAndView.setViewName("action_results/add_result");
        if (dictionaryService.add(key, value, selectedDictionary)) {
            modelAndView.addObject(RESULT, SUCCESS);
        } else {
            modelAndView.addObject(RESULT, ERROR);
        }
        return modelAndView;
    }

    @GetMapping("/read")
    public ModelAndView read(@RequestParam(value = "dictionaryId") int dictionaryId, ModelAndView modelAndView) {

        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            modelAndView.addObject(ERROR_LANGUAGE, NO_EXIST_LANGUAGE);
        }

        List<DictionaryLine> readResult = dictionaryService.read(selectedDictionary);
        modelAndView.setViewName("action_results/read_result");
        modelAndView.addObject(ID, dictionaryId);
        modelAndView.addObject(RESULT, readResult);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam String key,
                         @RequestParam(value = "dictionaryId") int dictionaryId, ModelAndView modelAndView) {
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            modelAndView.addObject(ERROR_LANGUAGE, NO_EXIST_LANGUAGE);
        }
        
        DictionaryLine searchResult = dictionaryService.search(key, selectedDictionary);
        modelAndView.setViewName("action_results/search_result");
        modelAndView.addObject(ID, dictionaryId);
        modelAndView.addObject(RESULT, searchResult);
        return modelAndView;
    }

    @PostMapping("/remove")
    public ModelAndView remove(@RequestParam String key,
                         @RequestParam(value = "dictionaryId") int dictionaryId, ModelAndView modelAndView) {
        modelAndView.addObject(ID, dictionaryId);
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            modelAndView.addObject(ERROR_LANGUAGE, NO_EXIST_LANGUAGE);
        }

        modelAndView.setViewName("action_results/remove_result");
        if (dictionaryService.remove(key, selectedDictionary)) {
            modelAndView.addObject(RESULT, DELETE);
        } else {
            modelAndView.addObject(RESULT, NO_DELETE);
        }
        return modelAndView;
    }

    @ModelAttribute("action")
    public String returnLib(){
        return "&action=menu";
    }

    @ModelAttribute("startAction")
    public String startLib(){
        return "/menu-controller/action-menu/?dictionaryId=";
    }
}
