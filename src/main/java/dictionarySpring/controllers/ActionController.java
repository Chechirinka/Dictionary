package dictionarySpring.controllers;

import dictionarySpring.configuration.DictionaryType;
import dictionarySpring.exception.TypeNotFoundException;
import dictionarySpring.model.DictionaryLine;
import dictionarySpring.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class ActionController{
    private final static String NO_EXIST_LANGUAGE = "Ошибка, такого языка не существует, повторите ввод!";
    private final static String SUCCESS = "Success";
    public final static String ERROR = "Error";
    private final static String DELETE = "Delete";
    private final static String NO_DELETE = "No delete";
    private final DictionaryService dictionaryService;
    private final DictionaryLine dictionaryLine;
    private DictionaryType selectedDictionary;


    @Autowired
    public ActionController(DictionaryService dictionaryService, DictionaryLine dictionaryLine) {
        this.dictionaryService = dictionaryService;
        this.dictionaryLine = dictionaryLine;
    }

    @GetMapping("read")
    @ResponseBody
    public ResponseEntity<?> read(@RequestParam(value = "id") int id) {
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(id);
        } catch (TypeNotFoundException e) {
            System.out.println(NO_EXIST_LANGUAGE);
        }
        return new ResponseEntity<>(dictionaryService.readService(selectedDictionary), HttpStatus.OK);
    }

    @PostMapping("write")
    @ResponseBody
    public ResponseEntity<?> write(@RequestParam(value = "id") int id,
                                   @RequestBody DictionaryLine dictionaryLine) {
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(id);
        } catch (TypeNotFoundException e) {
            System.out.println(NO_EXIST_LANGUAGE);
        }
        if (dictionaryService.addService(dictionaryLine.getKey(), dictionaryLine.getValue(), selectedDictionary)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(ERROR, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("search")
    public ResponseEntity search(@RequestParam(value = "id") int id,
                                 @RequestParam(value = "key") String key) {
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(id);
        } catch (TypeNotFoundException e) {
            System.out.println(NO_EXIST_LANGUAGE);
        }
        return dictionaryService.searchService(key, selectedDictionary);
    }

    @PostMapping("remove")
    @ResponseBody
    public ResponseEntity<?> remove(@RequestParam(value = "id") int id,
                                    @RequestParam(value = "key") String key){
        try {
            selectedDictionary = DictionaryType.getDictionaryTypeByNumber(id);
        } catch (TypeNotFoundException e) {
            System.out.println(NO_EXIST_LANGUAGE);
        }
        if (dictionaryService.removeService(key, selectedDictionary)) {
            return new ResponseEntity<>(DELETE, HttpStatus.OK);
        }
        return new ResponseEntity<>(NO_DELETE, HttpStatus.BAD_REQUEST);
    }
}