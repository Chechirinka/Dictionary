package dictionarySpring.controllers;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.exception.TypeNotFoundException;
import dictionarySpring.service.DictionaryService;
import dictionarySpring.service.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private Formation formation;

    @Autowired
    public ActionControllerMvc(DictionaryService dictionaryService, Formation formation) {
        this.dictionaryService = dictionaryService;
        this.formation = formation;
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
        if (dictionaryService.add(key, value, selectedDictionary)) {
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
        List<String> readResult = formation.castToString(dictionaryService.read(selectedDictionary));

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
        String searchResult = formation.castToString(dictionaryService.search(key, selectedDictionary));
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
        if (dictionaryService.remove(key, selectedDictionary)) {
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
