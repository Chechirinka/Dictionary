package dictionarySpring.controllers;

import dictionarySpring.exception.TypeNotFoundException;
import dictionarySpring.model.dictionaryType.DictionaryType;
import dictionarySpring.model.modelDefault.DictionaryLine;
import dictionarySpring.service.DictionaryService;
import dictionarySpring.service.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/action")
@Transactional
public class ActionControllerMvc {

    private final static String SUCCESS = "Success";
    private final static String ERROR = "Error";
    private final static String DELETE = "Удалено";
    private final static String NO_DELETE = "Не удалено";
    private final static String RESULT = "result";
    public final static String ID = "dictionaryId";

    private final DictionaryService dictionaryService;


    private Formation formation;

    private ModelAndView modelAndView;

    @Autowired
    public ActionControllerMvc(DictionaryService dictionaryService, Formation formation) {
        this.dictionaryService = dictionaryService;
        this.formation = formation;
    }

    @PostMapping("/add")
    public String add(@RequestParam(value = "key") String key,
                      @RequestParam(value = "value") String value,
                      @RequestParam(value = "selectedDictionaryFrom") String selectedDictionaryFrom,
                      @RequestParam(value = "selectedDictionaryTo") String selectedDictionaryTo,
                      Model model) {

        if (dictionaryService.add(key, value, selectedDictionaryFrom, selectedDictionaryTo)) {
            model.addAttribute(RESULT, SUCCESS);
        } else {
            model.addAttribute(RESULT, ERROR);
        }
        return "action_results/add_result";
    }

    @GetMapping("/read")
    public String read(@RequestParam(value = "selectedDictionaryFrom") String selectedDictionaryFrom,
                       @RequestParam(value = "selectedDictionaryTo") String selectedDictionaryTo,
                       Model model) {
        List<String> readResult = formation.castToString(dictionaryService.read(selectedDictionaryFrom, selectedDictionaryTo));

        model.addAttribute(RESULT, readResult);
        return "action_results/read_result";
    }

    @GetMapping("/search")
    public String search(@RequestParam String key,
                         @RequestParam(value = "selectedDictionaryFrom") String selectedDictionaryFrom,
                         @RequestParam(value = "selectedDictionaryTo") String selectedDictionaryTo,
                         Model model) {
        String searchResult = formation.castToString(dictionaryService.search(key, selectedDictionaryFrom, selectedDictionaryTo));
        model.addAttribute(RESULT, searchResult);
        return "action_results/search_result";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam String key,
                         @RequestParam(value = "selectedDictionaryFrom") String selectedDictionaryFrom,
                         @RequestParam(value = "selectedDictionaryTo") String selectedDictionaryTo,
                         Model model) {

        if (dictionaryService.remove(key, selectedDictionaryFrom, selectedDictionaryTo)) {
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
