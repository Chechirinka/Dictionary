package dictionarySpring.controllers;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.dao.DictionaryDAO;
import dictionarySpring.exception.TypeNotFoundException;
import dictionarySpring.model.DictionaryLine;
import dictionarySpring.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/action")
@Transactional
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
    public String add(@RequestParam(value = "key") String key,
                        @RequestParam(value = "value") String value,
                        @RequestParam(value = "dictionaryId") int dictionaryId, Model model) {
        model.addAttribute(ID, dictionaryId);
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            model.addAttribute(ERROR_LANGUAGE, NO_EXIST_LANGUAGE);
        }
        if (dictionaryService.addService(key, value, selectedDictionary)) {
            model.addAttribute(RESULT, SUCCESS);
        } else {
            model.addAttribute(RESULT, ERROR);
        }
        return "action_results/add_result";
    }

    @GetMapping("/read")
    public String read(@RequestParam(value = "dictionaryId") int dictionaryId,
                       Model model) {
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            model.addAttribute(ERROR_LANGUAGE, NO_EXIST_LANGUAGE);
        }
        List<String> readResult = dictionaryService.readService(selectedDictionary);

        model.addAttribute(ID, dictionaryId);
        model.addAttribute(RESULT, readResult);
        return "action_results/read_result";
    }

    @GetMapping("/search")
    public String search(@RequestParam String key,
                         @RequestParam(value = "dictionaryId") int dictionaryId, Model model) {
        model.addAttribute(ID, dictionaryId);
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            model.addAttribute(ERROR_LANGUAGE, NO_EXIST_LANGUAGE);
        }
        String searchResult = dictionaryService.searchService(key, selectedDictionary);
        model.addAttribute(RESULT, searchResult);
        return "action_results/search_result";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam String key,
                         @RequestParam(value = "dictionaryId") int dictionaryId, Model model) {
        model.addAttribute(ID, dictionaryId);
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(dictionaryId);
        } catch (TypeNotFoundException e) {
            model.addAttribute(ERROR_LANGUAGE, NO_EXIST_LANGUAGE);
        }
        if (dictionaryService.removeService(key, selectedDictionary)) {
            model.addAttribute(RESULT, DELETE);
        } else {
            model.addAttribute(RESULT, NO_DELETE);
        }
        return "action_results/remove_result";
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
